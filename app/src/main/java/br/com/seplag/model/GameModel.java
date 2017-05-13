package br.com.seplag.model;

import java.io.Serializable;

/**
 * Created by Manoel Neto on 02/05/2017.
 */

public class GameModel implements Serializable{
    private int game_id;
    private String area_questions;
    private String name_list;
    private String date_post;
    private boolean active_list;

    public boolean isActive_list() {
        return active_list;
    }

    public void setActive_list(boolean active_list) {
        this.active_list = active_list;
    }

    public String getArea_questions() {
        return area_questions;
    }

    public void setArea_questions(String area_questions) {
        this.area_questions = area_questions;
    }

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public String getDate_post() {
        return date_post;
    }

    public void setDate_post(String date_post) {
        this.date_post = date_post;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "game_id=" + game_id +
                ", area_questions='" + area_questions + '\'' +
                ", name_list='" + name_list + '\'' +
                ", date_post='" + date_post + '\'' +
                ", active_list=" + active_list +
                '}';
    }
}
