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

    private String[] arrayNeighborhoods = {
            "1º Distrito", "2º Distrito", "3º Distrito", "4º Distrito", "Agamenon Magalhães",
            "Alto do Moura", "Boa Vista", "Caiucá", "Centenário", "Cidade Alta", "Cidade Jardim",
            "Deputado José Antônio Liberato", "Divinópolis", "Indianópolis", "Jardim Panorama",
            "João Mota", "José Carlos de Oliveira", "Kennedy", "Luiz Gonzaga", "Maria Auxiliadora",
            "Maurício de Nassau", "Monte Bom Jesus", "Nossa Senhora das Dores", "Nova Caruaru",
            "Nina Liberato", "Petrópolis", "Peladas", "Rendeiras", "Riachão", "Salgado", "Santa Rosa",
            "São Francisco", "São João da Escócia", "São José", "Serras do Vale", "Universitário",
            "Vassoural", "Verde"
    };

    public String[] getArrayProgram() {
        return arrayProgram;
    }

    public String[] getArrayNeighborhoods() {
        return arrayNeighborhoods;
    }
}
