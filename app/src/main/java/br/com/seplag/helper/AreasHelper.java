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
                areaPPA = "Participação da Mulher";
            }
            if (position == 8) {
                areaPPA = "Direitos Humanos";
            }
        }

        if (eixo.equals("eixo2")) {
            if (position == 3) {
                areaPPA = "Investimentos e Fortalecimento da Feira";
            }
            if (position == 4) {
                areaPPA = "Meio Ambiente";
            }
            if (position == 5) {
                areaPPA = "Emprego e Renda";
            }
            if (position == 6) {
                areaPPA = "Desenvolvimento Rural";
            }
            if (position == 7) {
                areaPPA = "Turismo e Cultura";
            }
        }

        if (eixo.equals("eixo3")) {
            if (position == 3) {
                areaPPA = "Infraestrutura e Saneamento";
            }
            if (position == 4) {
                areaPPA = "Ordem Pública";
            }
            if (position == 5) {
                areaPPA = "Mobilidade e Transporte";
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
                areaPPA = "Participação da Sociedade";
            }
            if (position == 4) {
                areaPPA = "Gestão Municipal";
            }
            if (position == 5) {
                areaPPA = "Planejamento Próximo ao Cidadão";
            }
            if (position == 6) {
                areaPPA = "Aumentar Eficiência do Município";
            }
        }

        return areaPPA;
    }
}
