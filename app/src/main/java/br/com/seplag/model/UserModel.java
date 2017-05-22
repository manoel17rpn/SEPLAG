package br.com.seplag.model;

import java.io.Serializable;

/**
 * Created by Manoel Neto on 29/04/2017.
 */

public class UserModel implements Serializable{

    private int user_id;
    private String user_name;
    private String user_phone;
    private String user_neighborhood;
    private String user_street;
    private int user_score;
    private String user_sex;
    private String user_income;
    private String user_scholarity;
    private String numbers_residents;
    private String user_office;
    private String user_invite;
    private String user_age;

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_invite() {
        return user_invite;
    }

    public void setUser_invite(String user_invite) {
        this.user_invite = user_invite;
    }

    public UserModel() {
    }

    public UserModel(String user_name, String user_phone, String user_neighborhood) {
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_neighborhood = user_neighborhood;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_neighborhood() {
        return user_neighborhood;
    }

    public void setUser_neighborhood(String user_neighborhood) {
        this.user_neighborhood = user_neighborhood;
    }

    public String getUser_street() {
        return user_street;
    }

    public void setUser_street(String user_street) {
        this.user_street = user_street;
    }

    public int getUser_score() {
        return user_score;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_income() {
        return user_income;
    }

    public void setUser_income(String user_income) {
        this.user_income = user_income;
    }

    public String getUser_scholarity() {
        return user_scholarity;
    }

    public void setUser_scholarity(String user_scholarity) {
        this.user_scholarity = user_scholarity;
    }

    public String getNumbers_residents() {
        return numbers_residents;
    }

    public void setNumbers_residents(String numbers_residents) {
        this.numbers_residents = numbers_residents;
    }

    public String getUser_office() {
        return user_office;
    }

    public void setUser_office(String user_office) {
        this.user_office = user_office;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_neighborhood='" + user_neighborhood + '\'' +
                ", user_street='" + user_street + '\'' +
                '}';
    }
}
