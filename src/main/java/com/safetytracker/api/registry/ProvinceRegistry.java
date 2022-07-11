package com.safetytracker.api.registry;

import com.safetytracker.api.model.ProvinceInfo;
import com.safetytracker.api.registry.BRAnalysisRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProvinceRegistry {
    public static Map<String, ProvinceInfo> REGISTRY = new HashMap<>();
    public static Map<String, Integer> BR_MAIS_PERIGOSA = new HashMap<>();
    public static Map<String, Integer> HORARIO_MAIS_PERIGOSO = new HashMap<>();

    public static void setup() {
        Map<String, ProvinceInfo> registry = new HashMap<>();
        Arrays.asList("AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO")
                .forEach(province -> registry.put(province, new ProvinceInfo(province)));
        REGISTRY = Collections.unmodifiableMap(registry);
        registry.values().forEach(value -> {
            try {
                //value.downloaDataset();
                //value.downloadModel();
                BRAnalysisRegistry.getAnalysisMap().put(value.getProvince(), new HashMap<>());
                value.loadSpecificData();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        BR_MAIS_PERIGOSA.put("AC", 364);
        HORARIO_MAIS_PERIGOSO.put("AC", 18);
        BR_MAIS_PERIGOSA.put("AL", 101);
        HORARIO_MAIS_PERIGOSO.put("AL", 18);
        BR_MAIS_PERIGOSA.put("AM", 174);
        HORARIO_MAIS_PERIGOSO.put("AM", 16);
        BR_MAIS_PERIGOSA.put("AP", 210);
        HORARIO_MAIS_PERIGOSO.put("AP", 19);
        BR_MAIS_PERIGOSA.put("BA", 101);
        HORARIO_MAIS_PERIGOSO.put("BA", 18);
        BR_MAIS_PERIGOSA.put("CE", 101);
        HORARIO_MAIS_PERIGOSO.put("CE", 18);
        BR_MAIS_PERIGOSA.put("DF", 20);
        HORARIO_MAIS_PERIGOSO.put("DF", 7);
        BR_MAIS_PERIGOSA.put("ES", 101);
        HORARIO_MAIS_PERIGOSO.put("ES", 18);
        BR_MAIS_PERIGOSA.put("GO", 153);
        HORARIO_MAIS_PERIGOSO.put("GO", 18);
        BR_MAIS_PERIGOSA.put("MA", 135);
        HORARIO_MAIS_PERIGOSO.put("MA", 19);
        BR_MAIS_PERIGOSA.put("MG", 163);
        HORARIO_MAIS_PERIGOSO.put("MG", 18);
        BR_MAIS_PERIGOSA.put("MS", 163);
        HORARIO_MAIS_PERIGOSO.put("MS", 18);
        BR_MAIS_PERIGOSA.put("MT", 364);
        HORARIO_MAIS_PERIGOSO.put("MT", 17);
        BR_MAIS_PERIGOSA.put("PA", 316);
        HORARIO_MAIS_PERIGOSO.put("PA", 19);
        BR_MAIS_PERIGOSA.put("PB", 230);
        HORARIO_MAIS_PERIGOSO.put("PB", 18);
        BR_MAIS_PERIGOSA.put("PE", 101);
        HORARIO_MAIS_PERIGOSO.put("PE", 18);
        BR_MAIS_PERIGOSA.put("PI", 343);
        HORARIO_MAIS_PERIGOSO.put("PI", 18);
        BR_MAIS_PERIGOSA.put("PR", 277);
        HORARIO_MAIS_PERIGOSO.put("PR", 18);
        BR_MAIS_PERIGOSA.put("RJ", 101);
        HORARIO_MAIS_PERIGOSO.put("RJ", 18);
        BR_MAIS_PERIGOSA.put("RN", 101);
        HORARIO_MAIS_PERIGOSO.put("RN", 18);
        BR_MAIS_PERIGOSA.put("RO", 364);
        HORARIO_MAIS_PERIGOSO.put("RO", 18);
        BR_MAIS_PERIGOSA.put("RR", 174);
        HORARIO_MAIS_PERIGOSO.put("RR", 18);
        BR_MAIS_PERIGOSA.put("RS", 116);
        HORARIO_MAIS_PERIGOSO.put("RS", 18);
        BR_MAIS_PERIGOSA.put("SC", 101);
        HORARIO_MAIS_PERIGOSO.put("SC", 18);
        BR_MAIS_PERIGOSA.put("SE", 101);
        HORARIO_MAIS_PERIGOSO.put("SE", 18);
        BR_MAIS_PERIGOSA.put("SP", 116);
        HORARIO_MAIS_PERIGOSO.put("SP", 18);
        BR_MAIS_PERIGOSA.put("TO", 153);
        HORARIO_MAIS_PERIGOSO.put("TO", 19);
    }
}
