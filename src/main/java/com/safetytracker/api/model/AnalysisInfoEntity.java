package com.safetytracker.api.model;

public class AnalysisInfoEntity {
    private String weekday;
    private String daytime;
    private String weather;
    private String day_phase;
    private AnalysisRouteEntity[] route;

    public AnalysisInfoEntity(String br, String diaDaSemana, String horario, String condicoesMetereologicas, String faseDia) {
        this.weekday = diaDaSemana;
        this.daytime = horario;
        this.weather = condicoesMetereologicas;
        this.day_phase = faseDia;
    }

    public AnalysisRouteEntity[] getRoute() {
        return route;
    }

    public String getDiaDaSemana() {
        return weekday;
    }

    public String getHorario() {
        return daytime;
    }

    public String getCondicoesMetereologicas() {
        return weather;
    }

    public String getFaseDia() {
        return day_phase;
    }
}
