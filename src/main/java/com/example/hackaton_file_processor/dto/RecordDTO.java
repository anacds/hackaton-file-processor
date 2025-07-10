package com.example.hackaton_file_processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
}