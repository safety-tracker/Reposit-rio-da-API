package com.safetytracker.api;

import com.safetytracker.api.registry.DownloadLinkRegistry;
import com.safetytracker.api.registry.ProvinceRegistry;
import com.safetytracker.api.registry.TrainedModelRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SafetyAPI {

    public static void main(String[] args) {
        System.out.println("Checking for model and dataset folders...");
        File dataset_folder = new File(SafetyAPI.class.getResource("datasets").getFile());
        if(!dataset_folder.exists()) {
            if(dataset_folder.mkdir()) {
                System.out.println("Datasets folder created");
            }
        }
        File models_folder = new File(SafetyAPI.class.getResource("ml_models").getFile());
        if(!models_folder.exists()) {
            if(models_folder.mkdir()) {
                System.out.println("ML models folder created");
            }
        }

        System.out.println("Loading provinces specific data...");
        double mili = System.currentTimeMillis();
        DownloadLinkRegistry.setup();
        ProvinceRegistry.setup();
        System.out.printf("Provinces specific data loaded in %.2f milliseconds.", System.currentTimeMillis() - mili);

        mili = System.currentTimeMillis();
        System.out.println("Loading trained ML models...");
        TrainedModelRegistry.setup();
        System.out.printf("Loaded ML models in %.2f milliseconds.", System.currentTimeMillis() - mili);

        SpringApplication.run(SafetyAPI.class, args);
    }

}
