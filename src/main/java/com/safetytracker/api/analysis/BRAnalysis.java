package com.safetytracker.api.analysis;

import com.safetytracker.api.registry.ProvinceRegistry;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class BRAnalysis {

    private int br;
    private String province;
    private HashMap<Integer, Integer> acidentByDaytime = new HashMap<>();
    private HashMap<String, Integer> accidentTypeRelation = new HashMap<>();
    private Map<Integer, HashMap<String, Integer>> accidentTypeByDaytime = new HashMap<>();
    private HashMap<String, Integer> accidentCauses = new HashMap<>();

    public Tuple<String, Integer> getAcidenteCausaMaisRecorrente() {
        final int[] qtd = {0};
        AtomicReference<String> maisRecorrente = new AtomicReference<>();
        accidentCauses.forEach((x,y) -> {
            if(y > qtd[0]) {
                qtd[0] = y;
                maisRecorrente.set(x);
            }
        });

        return new Tuple<String, Integer>(maisRecorrente.get(), qtd[0]);
    }

    public Tuple<String, Integer> getAcidenteMaisRecorrenteEmHorario(int time) {
        Map<String, Integer> tipos = accidentTypeByDaytime.get(time);
        final int[] qtd = {0};
        AtomicReference<String> maisRecorrente = new AtomicReference<>();
        tipos.forEach((x,y) -> {
            if(y > qtd[0]) {
                qtd[0] = y;
                maisRecorrente.set(x);
            }
        });

        return new Tuple<String, Integer>(maisRecorrente.get(), qtd[0]);
    }

    public Integer getHorarioMaisPerigoso() {
        int dangerous = 0;
        int hour = 0;
        for(int i = 0; i < 24; i++) {
            if(acidentByDaytime.get(i) > dangerous) {
                dangerous = acidentByDaytime.get(i);
                hour = i;
            }
        }
        return hour;
    }

    public Integer getHorarioMaisSeguro() {
        int safe = -989;
        int hour = 0;
        for(int i = 0; i < 24; i++) {
            if(acidentByDaytime.get(i) > safe) {
                if(safe == -989) {
                    safe = acidentByDaytime.get(i);
                    hour = i;
                    continue;
                }
                if(safe > acidentByDaytime.get(i)) {
                    safe = acidentByDaytime.get(i);
                    hour = i;
                }
            }
        }
        return hour;
    }

    public Tuple<String, Integer> getTipoMaisFrequente() {
        final int[] qtd = {0};
        AtomicReference<String> maisRecorrente = new AtomicReference<>();
        accidentTypeRelation.forEach((x,y) -> {
            if(y > qtd[0]) {
                qtd[0] = y;
                maisRecorrente.set(x);
            }
        });

        return new Tuple<>(maisRecorrente.get(), qtd[0]);
    }

    public BRAnalysis(int br, String province) {
        this.br = br;
        this.province = province;
        for(int i = 0; i < 24; i++) {
            acidentByDaytime.put(i, 0);
            accidentTypeByDaytime.put(i, new HashMap<>());
        }
    }

    public void load() {
        System.out.println("Loading specific data for province " + province + "" + br);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("datasets/" + province + ".arff").getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null) {
                    if(line.startsWith("@") || line.isEmpty()) {
                        continue;
                    }
                    String[] data = line.split(",");
                    if(Integer.parseInt(data[2]) != this.br) {
                        continue;
                    }

                    int horario = Integer.parseInt(data[1]);
                    String cause = data[4];
                    String type = data[5];

                    int acidentesCount = acidentByDaytime.get(horario);
                    acidentesCount++;
                    acidentByDaytime.remove(horario);
                    acidentByDaytime.put(horario, acidentesCount);

                    if(!accidentTypeRelation.containsKey(type)) {
                        accidentTypeRelation.put(type, 1);
                    } else {
                        int accidentTypeCount = accidentTypeRelation.get(type);
                        accidentTypeCount++;
                        accidentTypeRelation.remove(type);
                        accidentTypeRelation.put(type, accidentTypeCount);
                    }

                    if(!accidentCauses.containsKey(cause)) {
                        accidentCauses.put(cause, 1);
                    } else {
                        int accidentCauseCount = accidentCauses.get(cause);
                        accidentCauseCount++;
                        accidentCauses.remove(cause);
                        accidentCauses.put(cause, accidentCauseCount);
                    }

                    HashMap<String, Integer> causasNoHorario = accidentTypeByDaytime.get(horario);
                    if(!causasNoHorario.containsKey(cause)) {
                        causasNoHorario.put(cause, 1);
                    } else {
                        int value = causasNoHorario.get(cause);
                        value++;
                        causasNoHorario.remove(cause);
                        causasNoHorario.put(cause, value);
                    }
                    accidentTypeByDaytime.remove(horario);
                    accidentTypeByDaytime.put(horario, causasNoHorario);


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    public static class ValueComparator<T> implements Comparator<T> {
        Map<T, Integer> base;

        public ValueComparator(Map<T, Integer> base) {
            this.base = base;
        }

        public int compare(T a, T b) {
            if (base.get(a) <= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static class Tuple<T,U> {
        private T left;
        private U right;

        public Tuple(T left, U right) {
            this.left = left;
            this.right = right;
        }

        public T getLeft() {
            return left;
        }

        public void setLeft(T left) {
            this.left = left;
        }

        public U getRight() {
            return right;
        }

        public void setRight(U right) {
            this.right = right;
        }
    }

}
