package com.safetytracker.api.registry;

import com.safetytracker.api.model.ProvinceInfo;
import org.springframework.core.io.ClassPathResource;
import weka.classifiers.trees.RandomForest;
import weka.core.SerializationHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TrainedModelRegistry {
    public static Map<String, RandomForest> REGISTRY = new HashMap<>();

    public static void setup() {
        Arrays.asList("AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO")
                .forEach(province -> {
                    try {
                        REGISTRY.put(province, (RandomForest) SerializationHelper.read(new ClassPathResource("ml_models/" + province + ".model").getInputStream()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
