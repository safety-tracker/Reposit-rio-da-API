package com.safetytracker.api.controller;

import com.safetytracker.api.analysis.BRAnalysis;
import com.safetytracker.api.model.*;
import com.safetytracker.api.registry.BRAnalysisRegistry;
import com.safetytracker.api.registry.ProvinceRegistry;
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
            ProvinceInfo provinceInfo = ProvinceRegistry.REGISTRY.get(info.getEstado());
            provinceInfo.loadSpecificData();
            Instances instances = provinceInfo.getDatasetInstances();

            RandomForest algorithm = (RandomForest) SerializationHelper.read(new ClassPathResource("ml_models/" + provinceInfo.getProvince() + ".model").getInputStream());
            Instance instance = new DenseInstance(10);
            instance.setDataset(instances);
            instance.setValue(0, info.getDiaDaSemana());
            instance.setValue(1, info.getHorario());
            instance.setValue(2, info.getBr());
            instance.setValue(3, info.getCidade());
            instance.setValue(5, info.getFaseDia());
            instance.setValue(6, info.getSentidoVia());
            instance.setValue(7, info.getCondicoesMetereologicas());
            instance.setValue(8, info.getTipoPista());
            instance.setValue(9, info.getTracadoVia());
            double[] values = algorithm.distributionForInstance(instance);

            int horario = Integer.parseInt(info.getHorario());
            BRAnalysis analiseDaBr = BRAnalysisRegistry.getAnalysisMap().get(info.getEstado()).get(Integer.parseInt(info.getBr()));

            ProvinceInfoResponse response = new ProvinceInfoResponse(
                    values[2]*100, values[0]*100, values[1]*100,
                    ProvinceRegistry.BR_MAIS_PERIGOSA.get(info.getEstado()),
                    ProvinceRegistry.HORARIO_MAIS_PERIGOSO.get(info.getEstado()),
                    analiseDaBr.getAcidenteCausaMaisRecorrente().getLeft(),
                    analiseDaBr.getTipoMaisFrequente().getLeft(),
                    analiseDaBr.getAcidenteMaisRecorrenteEmHorario(horario).getLeft(),
                    analiseDaBr.getHorarioMaisPerigoso(),
                    analiseDaBr.getHorarioMaisSeguro()
            );
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
