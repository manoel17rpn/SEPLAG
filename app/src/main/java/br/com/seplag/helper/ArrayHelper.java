package br.com.seplag.helper;

import java.util.Arrays;

/**
 * Created by Manoel Neto on 30/04/2017.
 */

public class ArrayHelper {

    //Caruaru Cidade Limpa
    private String[] arrayActivitiesCidadeLimpa = {
            "Fiscalização de Metralha/Entulho",
            "Varrição",
            "Limpeza de Rios/Córregos",
            "Capinação",
            "Coleta de Lixo"
    };

    private String[] arrayActivitiesIlumina = {
            "Troca de Lâmpadas",
            "Solicitação de Luminária"
    };

    private String[] NeighborhoodsUrbanArea = {
            "SÃO FRANCISCO", "MONTE DO BOM JESUS", "DIVINÓPOLIS", "CENTRO", "NOVA CARUARU", "SEVERINO AFONSO",
            "LUIZ GONZAGA", "MAURICIO DE NASSAU", "FERNANDO LYRA", "UNIVERSITÁRIO", "LAGOA DE ALGODÃO", "PARQUE DA CIDADE",
            "RENDEIRAS", "ALTO DA BALANÇA", "CEDRO", "CIDADE JARDIM", "RIACHÃO", "SALGADO", "SÃO JOÃO DA ESCÓCIA",
            "LOT. SERRANÓPOLIS", "MORADA NOVA", "SERRA DO VALE", "PETRÓPOLIS", "SANTA ROSA", "VASSOURAL",
            "JARDIM LIBERDADE", "ALTO DA BANANA", "INDIANÓPOLIS", "INOCOOP", "JOSÉ LIBERATO I E II", "AGAMENON MAGALHÃES", "WILTON LIRA",
            "PITOMBEIRAS I E II", "ENCANTO DA SERRA", "ADALGISA NUNES I, II E III", "ROSANÓPOLIS", "PINHEIRÓPOLIS",
            "VILA ANDORINHA", "MARIA AUXILIADORA", "BOA VISTA I E II", "JARDIM PANORAMA I E II", "JARDIM BOA VISTA",
            "CAIUCÁ", "JOÃO MOTA", "JOSÉ CARLOS DE OLIVEIRA", "VILA KENNEDY", "SOL POENTE", "VILA DO AEROPORTO", "DEMÓSTENES VERAS",
            "LOT. MOURA BRASIL", "LOT. JOÃO BARRETO", "LOT. NOVO MUNDO", "VILA PADRE INÁCIO"
    };

    private String[] DistrictOne = {
            "TAQUARA DE SÃO PEDRO", "ALECRIM", "SALINAS", "LOGRADOURO","PELADAS", "TAQUARA DE BAIXO", "TAQUARA DE CIMA",
            "POSSE", "ALTO DO MOURA", "SERRA DOS CAVALOS", "ARACÁ", "LAGOA DE PAULISTA", "BREJO VELHO", "TERRA VERMELHA",
            "PAU SANTO", "AGRESTE DE PAU SANTO", "VASCO", "FAZENDA MIRIM", "PÉ DE SERRA DE SÃO FRANCISCO", "CIPÓ", "MATA NEGRO",
            "CAMPO NOVO", "ENCANTO ESTIVAS", "SITIO PALMEIRAS", "SITIO JARARACA", "SITIO MOSQUITO", "SITIO FAZENDA CARUARU",
            "SITIO SANTA MARIA", "SITIO CAPOEIRÃO", "SITIO BREJO DO BURACO", "SITIO MURICÍ DE BAIXO", "SITIO PÉ DE SERRA",
            "SITIO RIACHO DO MELO", "SITIO OLHO D’ÁGUA DA CANA", "SITIO BREJINHO", "SITIO RUA DO MATO", "SITIO MASSARANDUBA",
            "SITIO LAGOINHA DE PEDRA", "SITIO SERRA DE SÃO FRANCISCO", "SITIO RIACHO MAGRO", "SITIO VEADO MAGRO", "SITIO RIACHO DA PALHA",
            "SITIO ILHA DE COBRAS", "SITIO TAQUARI", "SITIO SERRA DE SÃO FRANCISCO", "SITIO GOIABEIRAS", "SITIO GRUTA FUNDA", "SITIO BAIXIO",
            "SITIO RIACHO DA PALHA", "SITIO ILHAS DE COBRAS", "SITIO VERTENTES", "SITIO PITOMBEIRAS", "SITIO BREJO NOVO", "SITIO DA MULATA",
            "SITIO MACAMBIRA"
    };

    private String[] DistrictTwo = {
            "VILA DO RAFAEL", "SALGADINHO", "CARAPOTÓS", "CONTENDAS", "JURITÍ", "COIMBRA", "NORMANDIA", "QUEIMADA DO URUCÚ",
            "RAFAEL DE DENTRO", "MACAMBIRA BORBA", "JACARÉ GRANDE", "CALDEIRÃO", "REINADO", "CACHOEIRA DE TABOCA",
            "BILHAR DE TABOCA", "SANTA MARIA", "CALDAS", "LAJES", "VILA CANAÃ", "OLHO D’ÁGUA DE SÃO FELIX", "PATOS", "GAFIEIRA",
            "BARAUNA", "BORBA", "LAGOA ROÇADA", "OLHO D’ÁGUA DO PADRE", "JUÁ", "BARRINHO", "GRUTA FUNDA", "LAGOA SALGADA",
            "VILA CANAÃ", "CACHOEIRA SECA", "ASSENTAMENTO VIADA MORTA", "SITIO GEMEDOURO", "SITIO POÇO VELHO", "SITIO PÉ DE PEDRA",
            "SITIO RIACHO DOCE I ,II E III", "SITIO TRAPIÁ", "SERRA DA ÚRSULA", "LOTEAMENTO NOVO HORIZONTE", "SITIO ARARA",
            "SITIO ANTAS", "SITIO TRAVESSÃO", "SITIO LUIZ CARLOS", "SITIO CAIBEIRAS", "SALGADINHO", "SITIO PÉ DE MELANCIA", "SITIO GRAVATÁ",
            "SITIO DOIS RIACHOS", "SITIO BAIXIO", "SITIO POÇOS", "SITIO MANDACARU", "SITIO RAMADA", "ITAÚNA", "SITIO PENDENCIA",
            "SITIO LAGOA DO ALGODÃO", "SITIO PAPAGAIO", "POÇO DANTAS", "SITIO SERRA DO MEDO", "SITIO VOLTA GRANDE",
            "SITIO BARREIRAS DE QUEIMADAS", "LAGOA DO AGRESTE", "ASSENTAMENTO OLHO D’ÁGUA DO SÃO FELIX", "CAJÁ",
            "FAZENDA OVO NOVO", "SITIO BRASÍLIAS", "ALTO DA MALHADA", "SITIO PITOMBEIRAS DE TABOCAS", "PALMATÓRIA I E II"
    };

    private String[] DistrictThree = {
            "SERRA VERDE", "ANTAS", "BARBATÃO", "PREGUIÇA", "LAGOA SALGADA", "SERRA VELHA",
            "JUCÁ", "SERRA DOS PINTOS", "SAGUIM", "AZEVÉM", "RIACHÃO", "RIACHO DO VEADO",
            "GUARIBAS", "CAMPESTRE", "MALHADA DE PEDRA", "GONÇALVES FERREIRA", "JACARÉ DE GONÇALVES FERREIRA",
            "UMBURANA", "ANGELIM", "SITIO CAPIVARA DE BAIXO", "SITIO CAPIVARA DE BARRACO", "BARRA DO RIACHO",
            "SITIO MALHADAS DE CAVEIRAS", "SITIO CALDEIRÕES", "SITIO AMEIXAS", "SITIO TORRÕES", "DE GUARIBAS",
            "SITIO SERRA DO MARINHEIRO", "SITIO JUREMA", "SITIO ARARAS", "SITIO JURITI", "SITIO DA BANDEIRA",
            "SITIO LAGOA DA CRUZ", "SITIO TAPUIA", "SITIO IPA", "QUEIMADINHA"
    };

    private String[] DistrictFour = {
            "XICURU", "LAJEDO PRETO", "CAPIM", "MARIA CLARA", "BAIXIO DO CAPIM",
            "MARIBONDO", "FIRMEZA", "JIQUIRI", "CACIMBINHA", "SERROTE DOS BOIS", "LAGOA DE PEDRA",
            "LAGOA DE EXU", "EDEIROS", "XIQUE-XIQUE", "LAJEDO DO CEDRO",
            "JAPECANGA", "MACACO", "FUNDÃO", "SERRA DE SÃO BENTO", "PINGUEIRAS", "PÉ DE LADEIRA",
            "SITIO CAJAZEIRAS", "SITIO CHAFARIZ", "CACIMBINHA CERCADA"
    };

    private String[] arrayRegions = {
            "Zona Urbana", "1º Distrito", "2º Distrito", "3º Distrito", "4º Distrito"
    };

    public String[] getNeighborhoodsUrbanArea() {
        Arrays.sort(NeighborhoodsUrbanArea);
        return NeighborhoodsUrbanArea;
    }

    public String[] getDistrictOne() {
        Arrays.sort(DistrictOne);
        return DistrictOne;
    }

    public String[] getDistrictTwo() {
        Arrays.sort(DistrictTwo);
        return DistrictTwo;
    }

    public String[] getDistrictThree() {
        Arrays.sort(DistrictThree);
        return DistrictThree;
    }

    public String[] getDistrictFour() {
        Arrays.sort(DistrictFour);
        return DistrictFour;
    }

    public String[] getArrayActivitiesCidadeLimpa() {
        return arrayActivitiesCidadeLimpa;
    }

    public String[] getArrayActivitiesIlumina() {
        return arrayActivitiesIlumina;
    }

    public String[] getArrayRegions() {
        return arrayRegions;
    }
}
