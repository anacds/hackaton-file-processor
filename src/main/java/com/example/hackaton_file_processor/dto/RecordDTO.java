package com.example.hackaton_file_processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Data
public class RecordDTO {
    private String id_registro;
    private String tipo_documento;

    @JsonProperty("identificador_profissional")
    private String identificadorProfissional;

    @JsonProperty("estabelecimento_saude")
    private String estabelecimentoSaude;

    @NotBlank
    @NotNull
    @JsonProperty("identificador_individuo")
    private String identificadorIndividuo;

    @JsonProperty("estrategia")
    private String estrategia;

    private String pesquisa_clinica;

    @JsonProperty("anvisa_protocolo_estudo")
    private String anvisaProtocoloEstudo;

    @JsonProperty("anvisa_protocolo_versao")
    private String anvisaProtocoloVersao;

    @JsonProperty("anvisa_num_registro")
    private String anvisaNumRegistro;

    @JsonProperty("data_imunizacao")
    private String dataImunizacao;

    @JsonProperty("imunobiologico")
    private String imunobiologico;

    @JsonProperty("fabricante")
    private String fabricante;

    @JsonProperty("lote")
    private String lote;

    @JsonProperty("dose")
    private String dose;

    @JsonProperty("via_administracao")
    private String viaAdministracao;

    @JsonProperty("local_aplicacao")
    private String localAplicacao;

    public String getId_registro() {
        return id_registro;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public String getIdentificadorProfissional() {
        return identificadorProfissional;
    }

    public String getEstabelecimentoSaude() {
        return estabelecimentoSaude;
    }

    public String getIdentificadorIndividuo() {
        return identificadorIndividuo;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public String getPesquisa_clinica() {
        return pesquisa_clinica;
    }

    public String getAnvisaProtocoloEstudo() {
        return anvisaProtocoloEstudo;
    }

    public String getAnvisaProtocoloVersao() {
        return anvisaProtocoloVersao;
    }

    public String getAnvisaNumRegistro() {
        return anvisaNumRegistro;
    }

    public String getDataImunizacao() {
        return dataImunizacao;
    }

    public String getImunobiologico() {
        return imunobiologico;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getLote() {
        return lote;
    }

    public String getDose() {
        return dose;
    }

    public String getViaAdministracao() {
        return viaAdministracao;
    }

    public String getLocalAplicacao() {
        return localAplicacao;
    }
}