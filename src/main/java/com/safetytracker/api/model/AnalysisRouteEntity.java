package com.safetytracker.api.model;

public class AnalysisRouteEntity {
    private String cidade;
    private String br;
    private String estado;

    public AnalysisRouteEntity(String cidade, String br, String estado) {
        this.cidade = cidade;
        this.br = br;
        this.estado = estado;
    }



    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
