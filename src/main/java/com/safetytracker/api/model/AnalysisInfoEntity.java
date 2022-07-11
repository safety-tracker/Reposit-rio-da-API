package com.safetytracker.api.model;

public class AnalysisInfoEntity {
    private String estado;
    private String br;
    private String diaDaSemana;
    private String cidade;
    private String horario;
    private String sentidoVia;
    private String condicoesMetereologicas;
    private String tipoPista;
    private String tracadoVia;
    private String faseDia;

    public AnalysisInfoEntity(String estado, String br, String diaDaSemana, String cidade, String horario, String sentidoVia, String condicoesMetereologicas, String tipoPista, String tracadoVia, String faseDia) {
        this.estado = estado;
        this.br = br;
        this.diaDaSemana = diaDaSemana;
        this.cidade = cidade;
        this.horario = horario;
        this.sentidoVia = sentidoVia;
        this.condicoesMetereologicas = condicoesMetereologicas;
        this.tipoPista = tipoPista;
        this.tracadoVia = tracadoVia;
        this.faseDia = faseDia;
    }

    public String getEstado() {
        return estado;
    }

    public String getBr() {
        return br;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getCidade() {
        return cidade;
    }

    public String getHorario() {
        return horario;
    }

    public String getSentidoVia() {
        return sentidoVia;
    }

    public String getCondicoesMetereologicas() {
        return condicoesMetereologicas;
    }

    public String getTipoPista() {
        return tipoPista;
    }

    public String getTracadoVia() {
        return tracadoVia;
    }

    public String getFaseDia() {
        return faseDia;
    }
}
