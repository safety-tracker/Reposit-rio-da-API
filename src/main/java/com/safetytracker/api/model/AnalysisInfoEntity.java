package com.safetytracker.api.model;

public class AnalysisInfoEntity {
    private String diaDaSemana;
    private String horario;
    private String condicoesMetereologicas;
    private String faseDia;
    private AnalysisRouteEntity[] route;

    public AnalysisInfoEntity(String br, String diaDaSemana, String horario, String condicoesMetereologicas, String faseDia) {
        this.diaDaSemana = diaDaSemana;
        this.horario = horario;
        this.condicoesMetereologicas = condicoesMetereologicas;
        this.faseDia = faseDia;
    }

    public AnalysisRouteEntity[] getRoute() {
        return route;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public String getCondicoesMetereologicas() {
        return condicoesMetereologicas;
    }

    public String getFaseDia() {
        return faseDia;
    }
}
