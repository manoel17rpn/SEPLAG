package br.com.seplag.model;

import java.io.Serializable;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameOptionsModel implements Serializable{
    private int options_id;
    private String name_list;
    private String option_one;
    private String option_two;
    private String option_three;
    private String option_four;
    private String area_questions;

    public String getArea_questions() {
        return area_questions;
    }

    public void setArea_questions(String area_questions) {
        this.area_questions = area_questions;
    }

    public String getOption_three() {
        return option_three;
    }

    public void setOption_three(String option_three) {
        this.option_three = option_three;
    }

    public String getOption_four() {
        return option_four;
    }

    public void setOption_four(String option_four) {
        this.option_four = option_four;
    }

    public int getOptions_id() {
        return options_id;
    }

    public void setOptions_id(int options_id) {
        this.options_id = options_id;
    }

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public String getOption_one() {
        return option_one;
    }

    public void setOption_one(String option_one) {
        this.option_one = option_one;
    }

    public String getOption_two() {
        return option_two;
    }

    public void setOption_two(String option_two) {
        this.option_two = option_two;
    }

    @Override
    public String toString() {
        return "GameOptionsModel{" +
                "options_id=" + options_id +
                ", name_list='" + name_list + '\'' +
                ", option_one='" + option_one + '\'' +
                ", option_two='" + option_two + '\'' +
                ", option_three='" + option_three + '\'' +
                ", option_four='" + option_four + '\'' +
                '}';
    }
}
