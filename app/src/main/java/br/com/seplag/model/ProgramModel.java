package br.com.seplag.model;

/**
 * Created by Manoel Neto on 05/05/2017.
 */

public class ProgramModel {

    private int user_id;
    private String name_program;
    private String service_program;
    private String name_neighborhood;
    private String name_street;
    private String reference_point;
    private String user_comment;
    private String image;
    private String poste;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName_program() {
        return name_program;
    }

    public void setName_program(String name_program) {
        this.name_program = name_program;
    }

    public String getService_program() {
        return service_program;
    }

    public void setService_program(String service_program) {
        this.service_program = service_program;
    }

    public String getName_neighborhood() {
        return name_neighborhood;
    }

    public void setName_neighborhood(String name_neighborhood) {
        this.name_neighborhood = name_neighborhood;
    }

    public String getName_street() {
        return name_street;
    }

    public void setName_street(String name_street) {
        this.name_street = name_street;
    }

    public String getReference_point() {
        return reference_point;
    }

    public void setReference_point(String reference_point) {
        this.reference_point = reference_point;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }
}
