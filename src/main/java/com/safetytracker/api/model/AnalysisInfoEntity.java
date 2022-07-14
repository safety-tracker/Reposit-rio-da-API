package com.safetytracker.api.model;

public class AnalysisInfoEntity {
    private String weekday;
    private String daytime;
    private String weather;
    private String day_phase;
    private AnalysisRouteEntity[] route;

    public AnalysisInfoEntity(String weekday, String daytime, String weather, String day_phase, AnalysisRouteEntity[] route) {
        this.weekday = weekday;
        this.route = route;
        this.daytime = daytime;
        this.weather = weather;
        this.day_phase = day_phase;
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
