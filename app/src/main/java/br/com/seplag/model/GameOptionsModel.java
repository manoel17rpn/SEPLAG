package br.com.seplag.model;

import java.io.Serializable;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameOptionsModel implements Serializable{
    private int options_id;
    private String axis;
    private String option_one;
    private String option_two;
    private String option_three;
    private String option_four;
    private String area;

    public String getArea_questions() {
        return area;
    }

    public void setArea_questions(String area) {
        this.area = area;
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

    public String getAxis() {
        return axis;
    }

    public void setAxis(String name_list) {
        this.axis = name_list;
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
                ", axis='" + axis + '\'' +
                ", option_one='" + option_one + '\'' +
                ", option_two='" + option_two + '\'' +
                ", option_three='" + option_three + '\'' +
                ", option_four='" + option_four + '\'' +
                '}';
    }
}
