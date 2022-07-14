package com.safetytracker.api.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.safetytracker.api.analysis.BRAnalysis;
import com.safetytracker.api.model.*;
import com.safetytracker.api.registry.BRAnalysisRegistry;
import com.safetytracker.api.registry.ProvinceRegistry;
import com.safetytracker.api.registry.Tuple;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.util.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@RestController
public class AnalysisController {

    private static Queue<AnalysisInfoEntity> processingQueue = new LinkedBlockingQueue<>();

    static {

    }


    @RequestMapping(value="/classify", method = RequestMethod.POST)
    public ResponseEntity<ProvinceInfoResponse> classifyEntry(@RequestBody AnalysisInfoEntity info) {
        try {
            int daytime = Integer.parseInt(info.getHorario());
            String weekday = info.getDiaDaSemana();
            String weather = info.getCondicoesMetereologicas();

            RouteInfoResponse[] responses = new RouteInfoResponse[info.getRoute().length];
            Multimap<String, Tuple<Integer, AnalysisRouteEntity>> mappedByProvince = ArrayListMultimap.create();

            for(int i = 0; i < info.getRoute().length; i++) {
                mappedByProvince.put(info.getRoute()[i].getEstado(), new Tuple<>(i, info.getRoute()[i]));
            }

            // Classifica todos as rodovias do mesmo estado cujo modelo já está carregado na memória, economizando CPU
            mappedByProvince.keySet().forEach(province -> {
                try {
                    ProvinceInfo provinceInfo = ProvinceRegistry.REGISTRY.get(province);
                    Instances instances = provinceInfo.getDatasetInstances();
                    RandomForest algorithm = (RandomForest) SerializationHelper.read(new ClassPathResource("ml_models/" + provinceInfo.getProvince() + ".model").getInputStream());
                    mappedByProvince.get(province).forEach(tuple -> {
                        try {
                            AnalysisRouteEntity route = tuple.getRight();
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

                            responses[tuple.getLeft()] = new RouteInfoResponse(br, route.getEstado(),
                                    route.getCidade(),values[2]*100, values[0]*100, values[1]*100,
                                    analysis.getHorarioMaisPerigoso(), analysis.getAcidenteCausaMaisRecorrente().getLeft(),
                                    analysis.getTipoMaisFrequente().getLeft(),
                                    analysis.getAcidenteMaisRecorrenteEmHorario(Integer.parseInt(info.getHorario())).getLeft(),
                                    analysis.getHorarioMaisSeguro());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            double averageFatal = Arrays.stream(responses)
                    .map(RouteInfoResponse::getFatal_risk)
                    .reduce(0D, Double::sum)/responses.length;
            double averageIleso = Arrays.stream(responses)
                    .map(RouteInfoResponse::getIleso_risk)
                    .reduce(0D, Double::sum)/responses.length;
            double averageFerido = Arrays.stream(responses)
                    .map(RouteInfoResponse::getFerido_risk)
                    .reduce(0D, Double::sum)/responses.length;

            RouteInfoResponse dangerous = responses[0];
            double fatalAverage = 0;
            int fatalQuantityInDangerous = 0;
            for(RouteInfoResponse route : responses) {
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
                    fatalAverage = currentFatalAverage;
                }
            }

            ProvinceInfoResponse response = new ProvinceInfoResponse(daytime, weather, weekday,averageFatal, averageFerido, averageIleso, dangerous.getBr(), fatalAverage*100D, responses);

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
