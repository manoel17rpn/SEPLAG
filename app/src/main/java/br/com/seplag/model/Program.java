package br.com.seplag.model;

/**
 * Created by Manoel Neto on 05/04/2017.
 */

public class Program {
    private String titleProgram;
    private String infoProgram;
    private int imageProgram;


    public Program(String titleProgram, String infoProgram, int imageProgram) {

        this.titleProgram = titleProgram;
        this.infoProgram = infoProgram;
        this.imageProgram = imageProgram;
    }

    public String getTitleProgram() {
        return titleProgram;
    }

    public void setTitleProgram(String titleProgram) {
        this.titleProgram = titleProgram;
    }

    public String getInfoProgram() {
        return infoProgram;
    }

    public void setInfoProgram(String infoProgram) {
        this.infoProgram = infoProgram;
    }

    public int getImageProgram() {
        return imageProgram;
    }

    public void setImageProgram(int imageProgram) {
        this.imageProgram = imageProgram;
    }

    public Program() {

    }

}