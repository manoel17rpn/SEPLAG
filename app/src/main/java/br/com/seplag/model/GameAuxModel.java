package br.com.seplag.model;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameAuxModel {
    private int aux_id;
    private int user_id;
    private String name_list;
    private String user_answers;

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

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public String getUser_answers() {
        return user_answers;
    }

    public void setUser_answers(String user_answers) {
        this.user_answers = user_answers;
    }
}
