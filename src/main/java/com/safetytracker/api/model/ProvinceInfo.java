package com.safetytracker.api.model;

import com.safetytracker.api.analysis.BRAnalysis;
import com.safetytracker.api.registry.BRAnalysisRegistry;
import org.apache.commons.io.FileUtils;
import com.safetytracker.api.registry.DownloadLinkRegistry;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import weka.core.Attribute;
import weka.core.Instances;

import javax.swing.text.html.Option;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class ProvinceInfo {
    private String province;
    private static final List<String> POSSIBLE_WEEKDAYS = Arrays.asList("SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO");
    private static final List<String> POSSIBLE_DAYTIME = Arrays.asList("0","1","2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private static final List<String> POSSIBLE_WEATHERS = Arrays.asList("CHUVA","SOL","GAROA/CHUVISCO","NEVE","GRANIZO","CEU_CLARO","NEVOEIRO/NEBLINA","NUBLADO","VENTO","IGNORADO");
    private static final List<String> POSSIBLE_LANE_TYPE = Arrays.asList("SIMPLES", "MULTIPLA", "DUPLA");
    private static final List<String> POSSIBLE_LANE_TRACING = Arrays.asList("CURVA","TUNEL","NAO_INFORMADO","CRUZAMENTO","RETA","ROTATORIA","VIADUTO","DESVIO_TEMPORARIO","PONTE","INTERSECAO_DE_VIAS","RETORNO_REGULAMENTADO");
    private static final List<String> POSSIBLE_CLASSIFICATIONS = Arrays.asList("COM_VITIMAS_FERIDAS","SEM_VITIMAS","COM_VITIMAS_FATAIS");
    private static final List<String> POSSIBLE_DAY_PHASE = Arrays.asList("ANOITECER","AMANHECER","PLENA_NOITE","PLENO_DIA");
    private static final List<String> POSSIBLE_DIRECTIONS = Arrays.asList("DECRESCENTE","CRESCENTE","NAO_INFORMADO");
    private final Set<String> POSSIBLE_BRS = new HashSet<>();
    private final Set<String> POSSIBLE_COUNTIES = new HashSet<>();
    private File model;
    private File dataset;

    public ProvinceInfo(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public File getModel() {
        return model;
    }

    public File getDataset() {
        return dataset;
    }

    public Optional<File> getDatasetFile() {
        try {
            InputStream resource = new ClassPathResource("datasets/" + province + ".arff").getInputStream();
            File expectedFile = ResourceUtils.getFile("classpath:datasets/" + province + ".arff");
            if(!expectedFile.exists()) {
                return Optional.empty();
            }
            return Optional.of(expectedFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<File> getMLModelFile() {
            try {
                File expectedFile = ResourceUtils.getFile("classpath:ml_models/" + province + ".model");
                if(!expectedFile.exists()) {
                    return Optional.empty();
                }
                return Optional.of(expectedFile);
            } catch (Exception ex) {
                return Optional.empty();
            }
    }

    public Instances getDatasetInstances() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        Attribute diaSemana = new Attribute("dia_da_semana", POSSIBLE_WEEKDAYS);
        Attribute horario = new Attribute("horario", POSSIBLE_DAYTIME);
        Attribute br = new Attribute("br", new ArrayList<>(POSSIBLE_BRS));
        Attribute municipio = new Attribute("municipio", new ArrayList<>(POSSIBLE_COUNTIES));
        Attribute classificacao = new Attribute("classificacao", POSSIBLE_CLASSIFICATIONS);
        Attribute fase_dia = new Attribute("fase_dia", POSSIBLE_DAY_PHASE);
        Attribute condicao_metereologica = new Attribute("condicao_metereologica", POSSIBLE_WEATHERS);
        attributes.addAll(Arrays.asList(diaSemana, horario, br, municipio, classificacao, fase_dia, condicao_metereologica));

        Instances dataset = new Instances("TestSet", attributes, 1);
        dataset.setClassIndex(4);
        return dataset;
    }

    public void downloaDataset() {
        try {
            File target = new File(ProvinceInfo.class.getResource("datasets/" + province + ".arff").getFile());
            if(!target.exists()) {
                System.out.println("\nDataset of province " + province + " not found. Downloading it.");
                double millis = System.currentTimeMillis();
                target.createNewFile();
                FileUtils.copyURLToFile(new URL(DownloadLinkRegistry.MODEL_LINK.get(province)), target);
                System.out.println("Dataset of province " + province + " downloaded in " + (System.currentTimeMillis() - millis) + " ms");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void downloadModel() {
        try {
            File target = new File(ProvinceInfo.class.getResource("ml_models/" + province + ".model").getFile());
            if(!target.exists()) {
                System.out.println("Model for province " + province + " no found. Downloading it.");
                double millis = System.currentTimeMillis();
                target.createNewFile();
                FileUtils.copyURLToFile(new URL(DownloadLinkRegistry.DATASET_LINK.get(province)), target);
                System.out.println("Model for province " + province + " downloaded in " + (System.currentTimeMillis() - millis) + " ms");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadSpecificData() throws FileNotFoundException {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("datasets/" + province + ".arff").getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null) {
                    if(line.startsWith("@attribute municipio")) {
                        String counties = line.split("municipio")[1].trim();
                        counties = counties.replace("{", "").replace("}", "");
                        POSSIBLE_COUNTIES.addAll(Arrays.asList(counties.split(",")));
                    }
                    if(line.startsWith("@attribute br")) {
                        String brs = line.split("br")[1].trim();
                        brs = brs.replace("{", "").replace("}", "").replace("\"", "");
                        POSSIBLE_BRS.addAll(Arrays.asList(brs.split(",")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            POSSIBLE_BRS.forEach(x -> {
                BRAnalysis analysis = new BRAnalysis(Integer.parseInt(x), province);
                analysis.load();
                BRAnalysisRegistry.getAnalysisMap().get(province).put(Integer.parseInt(x), analysis);
            });


    }

    public Set<String> getPOSSIBLE_BRS() {
        return POSSIBLE_BRS;
    }

    public Set<String> getPOSSIBLE_COUNTIES() {
        return POSSIBLE_COUNTIES;
    }
}
