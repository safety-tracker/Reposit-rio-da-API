package com.safetytracker.api.controller;

import com.safetytracker.api.analysis.BRAnalysis;
import com.safetytracker.api.model.*;
import com.safetytracker.api.registry.BRAnalysisRegistry;
import com.safetytracker.api.registry.ProvinceRegistry;
import com.safetytracker.api.registry.TrainedModelRegistry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class AnalysisController {

    @RequestMapping(value="/classify", method = RequestMethod.POST)
    public ResponseEntity<ProvinceInfoResponse> classifyEntry(@RequestBody AnalysisInfoEntity info) {
        try {

            List<RouteInfoResponse> routeInfos = new ArrayList<>();

            for(AnalysisRouteEntity route : info.getRoute()) {
                ProvinceInfo provinceInfo = ProvinceRegistry.REGISTRY.get(route.getEstado());
                Instances instances = provinceInfo.getDatasetInstances();
                RandomForest algorithm = TrainedModelRegistry.REGISTRY.get(provinceInfo.getProvince());
                Instance instance = new DenseInstance(10);
                instance.setDataset(instances);
                instance.setValue(0, info.getDiaDaSemana());
                instance.setValue(1, info.getHorario());
                instance.setValue(2, route.getBr());
                instance.setValue(3, route.getCidade());
                instance.setValue(5, info.getFaseDia());
                instance.setValue(6, info.getCondicoesMetereologicas());
                double[] values = algorithm.distributionForInstance(instance);

                int br = Integer.parseInt(route.getBr());
                BRAnalysis analysis = BRAnalysisRegistry.getAnalysisMap().get(route.getEstado()).get(br);

                routeInfos.add(new RouteInfoResponse(br, route.getEstado(),
                        values[2]*100, values[0]*100, values[1]*100,
                        analysis.getHorarioMaisPerigoso(), analysis.getAcidenteCausaMaisRecorrente().getLeft(),
                        analysis.getTipoMaisFrequente().getLeft(),
                        analysis.getAcidenteMaisRecorrenteEmHorario(Integer.parseInt(info.getHorario())).getLeft(),
                        analysis.getHorarioMaisSeguro()));
            }

            double averageFatal = routeInfos.stream()
                    .map(RouteInfoResponse::getFatal_risk)
                    .reduce(0D, Double::sum)/routeInfos.size();
            double averageIleso = routeInfos.stream()
                    .map(RouteInfoResponse::getIleso_risk)
                    .reduce(0D, Double::sum)/routeInfos.size();
            double averageFerido = routeInfos.stream()
                    .map(RouteInfoResponse::getFerido_risk)
                    .reduce(0D, Double::sum)/routeInfos.size();

            RouteInfoResponse dangerous = routeInfos.get(0);
            double fatalAverage = 0;
            int fatalQuantityInDangerous = 0;
            for(RouteInfoResponse route : routeInfos) {
                BRAnalysis current = BRAnalysisRegistry.getAnalysisMap().get(dangerous.getProvince()).get(dangerous.getBr());
                BRAnalysis incoming = BRAnalysisRegistry.getAnalysisMap().get(route.getProvince()).get(route.getBr());
                double incomingFatalAverage = (double) incoming.getFatalAccidents()/incoming.getAccidentCount();
                if(current.equals(incoming)) {
                    fatalQuantityInDangerous = current.getFatalAccidents();
                    fatalAverage = incomingFatalAverage;
                    continue;
                }
                double currentFatalAverage = (double) current.getFatalAccidents()/current.getAccidentCount();
                if(currentFatalAverage < incomingFatalAverage) {
                    dangerous = route;
                }
            }

            ProvinceInfoResponse response = new ProvinceInfoResponse(averageFatal, averageFerido, averageIleso, dangerous.getBr(), fatalQuantityInDangerous, (RouteInfoResponse[]) routeInfos.toArray(new RouteInfoResponse[0]));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET");


            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value="/counties", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificCounties(@RequestParam String province) {
        ArrayList<CountieEntity> possible = ProvinceRegistry.REGISTRY.get(province).getPOSSIBLE_COUNTIES()
                .stream().map(CountieEntity::new).distinct().collect(Collectors.toCollection(ArrayList::new));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET");
        return new ResponseEntity<>(possible, headers, HttpStatus.OK);
    }

    @RequestMapping(value="/brs", method = RequestMethod.GET)
    public ResponseEntity<?> getSpecificBRs(@RequestParam String province) {
        System.out.println(province);
        System.out.println(ProvinceRegistry.REGISTRY.keySet());
        ArrayList<BREntity> possible = ProvinceRegistry.REGISTRY.get(province).getPOSSIBLE_BRS()
                .stream().map(x -> new BREntity(x, x)).collect(Collectors.toCollection(ArrayList::new));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET");
        return new ResponseEntity<>(possible, headers, HttpStatus.OK);
    }
}
