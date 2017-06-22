package br.com.seplag.helper;

/**
 * Created by manoelramos on 12/05/17.
 */

public class OfficeHelper {

    private String office_1 = "Assistente Virtual";
    private String office_2 = "Assistente Técnico Virtual";
    private String office_3 = "Coordenador Virtual";
    private String office_4 = "Chefe de Gabinete Virtual";
    private String office_5 = "Gerente Virtual";
    private String office_6 = "Gerente Geral Virtual";
    private String office_7 = "Secretário Executivo Virtual";
    private String office_8 = "Assessor Especial Virtual";
    private String office_9 = "Secretário de Governo Virtual";

    public String getOffice_1() {
        return office_1;
    }

    public String getOffice_2() {
        return office_2;
    }

    public String getOffice_3() {
        return office_3;
    }

    public String getOffice_4() {
        return office_4;
    }

    public String getOffice_5() {
        return office_5;
    }

    public String getOffice_6() {
        return office_6;
    }

    public String getOffice_7() {
        return office_7;
    }

    public String getOffice_8() {
        return office_8;
    }

    public String getOffice_9() {
        return office_9;
    }


    public String getOffice(int score){
        String office = "";
        if(score < 1000){
            office = getOffice_1();
        }else if(score >= 1000 && score < 2000){
            office = getOffice_2();
        }else if(score >= 2000 && score < 3000){
            office = getOffice_3();
        }else if(score >= 3000 && score < 4000){
            office = getOffice_4();
        }else if(score >= 4000 && score < 5000){
            office = getOffice_5();
        }else if(score >= 5000 && score < 6000){
            office = getOffice_6();
        }else if(score >= 6000 && score < 7000){
            office = getOffice_7();
        }else if(score >= 7000 && score < 10000){
            office = getOffice_8();
        }else if(score > 10000){
            office = getOffice_9();
        }

        return office;
    }
}
