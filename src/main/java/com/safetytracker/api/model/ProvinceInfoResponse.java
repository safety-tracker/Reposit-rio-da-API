package com.safetytracker.api.model;

public class ProvinceInfoResponse {
    private double riscoFatal;
    private double riscoFerido;
    private double riscoIleso;
    private int dangerousBr;
    private int dangerousTime;
    private String causaMaisComum;
    private String tipoMaisComum;
    private String tipoDeAcidenteMaisRecorente;
    private int horarioMaisPerigoso;
    private int horarioMaisSeguro;

    public ProvinceInfoResponse(double riscoFatal, double riscoFerido, double riscoIleso, int dangerousBr, int dangerousTime, String causaMaisComum, String tipoMaisComum, String tipoDeAcidenteMaisRecorente, int horarioMaisPerigoso, int horarioMaisSeguro) {
        this.riscoFatal = riscoFatal;
        this.riscoFerido = riscoFerido;
        this.riscoIleso = riscoIleso;
        this.dangerousBr = dangerousBr;
        this.dangerousTime = dangerousTime;
        this.causaMaisComum = causaMaisComum;
        this.tipoMaisComum = tipoMaisComum;
        this.tipoDeAcidenteMaisRecorente = tipoDeAcidenteMaisRecorente;
        this.horarioMaisPerigoso = horarioMaisPerigoso;
        this.horarioMaisSeguro = horarioMaisSeguro;
    }

    public String getCausaMaisComum() {
        return causaMaisComum;
    }

    public String getTipoMaisComum() {
        return tipoMaisComum;
    }

    public String getTipoDeAcidenteMaisRecorente() {
        return tipoDeAcidenteMaisRecorente;
    }

    public int getHorarioMaisPerigoso() {
        return horarioMaisPerigoso;
    }

    public int getHorarioMaisSeguro() {
        return horarioMaisSeguro;
    }

    public double getRiscoFatal() {
        return riscoFatal;
    }

    public double getRiscoFerido() {
        return riscoFerido;
    }

    public double getRiscoIleso() {
        return riscoIleso;
    }

    public int getDangerousBr() {
        return dangerousBr;
    }

    public int getDangerousTime() {
        return dangerousTime;
    }
}
