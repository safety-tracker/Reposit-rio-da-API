package com.safetytracker.api.model;

public class RouteInfoResponse {
    private int br;
    private String province;
    private double fatal_risk;
    private double ferido_risk;
    private double ileso_risk;
    private int dangerous_time;
    private String common_cause;
    private String common_type;
    private String common_causa_in_daytime;
    private int secure_daytime;

    public RouteInfoResponse(int br, String province, double fatal_risk, double ferido_risk, double ileso_risk, int dangerous_time, String common_cause, String common_type, String common_causa_in_daytime, int secure_daytime) {
        this.fatal_risk = fatal_risk;
        this.br = br;
        this.province = province;
        this.ferido_risk = ferido_risk;
        this.ileso_risk = ileso_risk;
        this.dangerous_time = dangerous_time;
        this.common_cause = common_cause;
        this.common_type = common_type;
        this.common_causa_in_daytime = common_causa_in_daytime;
        this.secure_daytime = secure_daytime;
    }

    public String getProvince() {
        return province;
    }

    public int getBr() {
        return br;
    }

    public double getFatal_risk() {
        return fatal_risk;
    }

    public void setFatal_risk(double fatal_risk) {
        this.fatal_risk = fatal_risk;
    }

    public double getFerido_risk() {
        return ferido_risk;
    }

    public void setFerido_risk(double ferido_risk) {
        this.ferido_risk = ferido_risk;
    }

    public double getIleso_risk() {
        return ileso_risk;
    }

    public void setIleso_risk(double ileso_risk) {
        this.ileso_risk = ileso_risk;
    }

    public int getDangerous_time() {
        return dangerous_time;
    }

    public void setDangerous_time(int dangerous_time) {
        this.dangerous_time = dangerous_time;
    }

    public String getCommon_cause() {
        return common_cause;
    }

    public void setCommon_cause(String common_cause) {
        this.common_cause = common_cause;
    }

    public String getCommon_type() {
        return common_type;
    }

    public void setCommon_type(String common_type) {
        this.common_type = common_type;
    }

    public String getCommon_causa_in_daytime() {
        return common_causa_in_daytime;
    }

    public void setCommon_causa_in_daytime(String common_causa_in_daytime) {
        this.common_causa_in_daytime = common_causa_in_daytime;
    }

    public int getSecure_daytime() {
        return secure_daytime;
    }

    public void setSecure_daytime(int secure_daytime) {
        this.secure_daytime = secure_daytime;
    }
}
