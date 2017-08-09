package br.com.seplag.model;

/**
 * Created by Manoel Neto on 05/05/2017.
 */

public class SolicitationsModel {

    private String program_name;
    private String service;
    private String neighborhood;
    private String street;
    private String reference_point;
    private String comment;
    private String latitude;
    private String longitude;
    private String bus;
    private Integer user_id;

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setReference_point(String reference_point) {
        this.reference_point = reference_point;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
