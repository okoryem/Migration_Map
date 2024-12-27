package com.migration_map;

public class UNHCRData {
    private int year;
    private String coo;
    private String coa;
    private int refugees;
    private int asylum_seekers;
    private int idps;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCoo() {
        return coo;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public String getCoa() {
        return coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    public int getRefugees() {
        return refugees;
    }

    public void setRefugees(int refugees) {
        this.refugees = refugees;
    }

    public int getAsylumSeekers() {
        return asylum_seekers;
    }

    public void setAsylumSeekers(int asylum_seekers) {
        this.asylum_seekers = asylum_seekers;
    }

    public int getIdps() {
        return idps;
    }

    public void setIdps(int idps) {
        this.idps = idps;
    }

    @Override
    public String toString() {
        return "UNHCRData{" +
                "year=" + year +
                ", coo='" + coo + '\'' +
                ", coa='" + coa + '\'' +
                ", refugees=" + refugees +
                ", asylum_seekers=" + asylum_seekers +
                ", idps=" + idps +
                '}';
    }
}
