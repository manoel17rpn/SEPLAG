package br.com.seplag.helper;

/**
 * Created by manoelramos on 15/06/17.
 */

public class AreasHelper {

    public String GetArea(String eixo, int position) {
        String areaPPA = "";

        if (eixo.equals("eixo1")) {
            if (position == 3) {
                areaPPA = "Saúde";
            }
            if (position == 4) {
                areaPPA = "Educação";
            }
            if (position == 5) {
                areaPPA = "Esporte e Lazer";
            }
            if (position == 6) {
                areaPPA = "Desenvolvimento Social";
            }
            if (position == 7) {
                areaPPA = "Mulher";
            }
            if (position == 8) {
                areaPPA = "Direitos Humanos";
            }
        }

        if (eixo.equals("eixo2")) {
            if (position == 3) {
                areaPPA = "Desenvolvimento Rural";
            }
            if (position == 4) {
                areaPPA = "Meio Ambiente";
            }
            if (position == 5) {
                areaPPA = "Emprego e Renda";
            }
            if (position == 6) {
                areaPPA = "Inovação";
            }
            if (position == 7) {
                areaPPA = "Cultura";
            }
        }

        if (eixo.equals("eixo3")) {
            if (position == 3) {
                areaPPA = "Obras e Serviços";
            }
            if (position == 4) {
                areaPPA = "Segurança";
            }
            if (position == 5) {
                areaPPA = "Estrada e Mobilidade";
            }
            if (position == 6) {
                areaPPA = "Moradia";
            }
            if (position == 7) {
                areaPPA = "A Caruaru do Futuro";
            }
        }

        if (eixo.equals("eixo4")) {
            if (position == 3) {
                areaPPA = "Transparência";
            }
            if (position == 4) {
                areaPPA = "Planejamento";
            }
            if (position == 5) {
                areaPPA = "Equilibrio Financeiro";
            }
            if (position == 6) {
                areaPPA = "Redução de Gastos";
            }
            if (position == 7) {
                areaPPA = "Aumento de Eficiência";
            }
        }

        return areaPPA;
    }
}
