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
        Arrays.asList("SP")
                .forEach(province -> {
                    try {
                        REGISTRY.put(province, (RandomForest) SerializationHelper.read(new ClassPathResource("ml_models/" + province + ".model").getInputStream()));
                        System.out.println("Loaded " + province + " ML model successfully.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
