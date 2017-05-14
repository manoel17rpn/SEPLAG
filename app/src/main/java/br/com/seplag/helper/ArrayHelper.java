package br.com.seplag.helper;

/**
 * Created by Manoel Neto on 30/04/2017.
 */

public class ArrayHelper {

    //Caruaru Cidade Limpa
    private String[] arrayProgram = {
            "Remoção de Entulhos",
            "Varrição",
            "Pintura de meio-fio",
            "Capinação"
    };

    private String[] NeighborhoodsUrbanArea = {
            "Salgado", "Cidade Alta"
    };

    private String[] DistrictOne = {
            "DistrictOne"
    };

    private String[] DistrictTwo = {
            "DistrictTwo"
    };

    private String[] DistrictThree = {
            "DistrictThree"
    };

    private String[] DistrictFour = {
            "DistrictFour"
    };

    public String[] getNeighborhoodsUrbanArea() {
        return NeighborhoodsUrbanArea;
    }

    public String[] getDistrictOne() {
        return DistrictOne;
    }

    public String[] getDistrictTwo() {
        return DistrictTwo;
    }

    public String[] getDistrictThree() {
        return DistrictThree;
    }

    public String[] getDistrictFour() {
        return DistrictFour;
    }

    private String[] arrayRegions = {
            "Zona Urbana", "1º Distrito", "2º Distrito", "3º Distrito", "4º Distrito"
    };

    public String[] getArrayProgram() {
        return arrayProgram;
    }

    public String[] getArrayRegions() {
        return arrayRegions;
    }
}
