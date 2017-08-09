package br.com.seplag.model;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameAuxModel {
    private int aux_id;
    private int user_id;
    private int options_id;
    private String area;
    private String user_answers;
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

    public int getAux_id() {
        return aux_id;
    }

    public void setAux_id(int aux_id) {
        this.aux_id = aux_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String name_list) {
        this.area = name_list;
    }

    public String getUser_answers() {
        return user_answers;
    }

    public void setUser_answers(String user_answers) {
        this.user_answers = user_answers;
    }

    public int getOptions_id() {
        return options_id;
    }

    public void setOptions_id(int options_id) {
        this.options_id = options_id;
    }

    @Override
    public String toString() {
        return "GameAuxModel{" +
                "aux_id=" + aux_id +
                ", user_id=" + user_id +
                ", options_id=" + options_id +
                ", name_list='" + area + '\'' +
                ", user_answers='" + user_answers + '\'' +
                '}';
    }
}
