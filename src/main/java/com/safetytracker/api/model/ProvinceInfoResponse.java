package com.safetytracker.api.model;

public class ProvinceInfoResponse {
    private double average_fatal;
    private double average_ferido;
    private double average_ileso;
    private int most_dangerous_br;
    private double fatal_average_in_dangerous_br;
    private RouteInfoResponse[] route_infos;

    public ProvinceInfoResponse(double average_fatal, double average_ferido, double average_ileso, int most_dangerous_br, double fatal_average_in_dangerous_br, RouteInfoResponse[] route_infos) {
        this.average_fatal = average_fatal;
        this.fatal_average_in_dangerous_br = fatal_average_in_dangerous_br;
        this.average_ferido = average_ferido;
        this.average_ileso = average_ileso;
        this.most_dangerous_br = most_dangerous_br;
        this.route_infos = route_infos;
    }

    public double getAverage_fatal() {
        return average_fatal;
    }

    public double getAverage_ferido() {
        return average_ferido;
    }

    public double getAverage_ileso() {
        return average_ileso;
    }

    public int getMost_dangerous_br() {
        return most_dangerous_br;
    }

    public double getFatal_average_in_dangerous_br() {
        return fatal_average_in_dangerous_br;
    }

    public RouteInfoResponse[] getRoute_infos() {
        return route_infos;
    }
}
