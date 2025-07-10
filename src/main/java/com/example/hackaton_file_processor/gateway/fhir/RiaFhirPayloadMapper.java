package com.example.hackaton_file_processor.gateway.fhir;

import com.example.hackaton_file_processor.dto.RecordDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RiaFhirPayloadMapper implements FhirPayloadMapper {

    private final ObjectMapper objectMapper;

    @Override
    public String map(RecordDTO dto) {
        try {
            var now = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            var bundle = Map.of(
                    "resourceType", "Bundle",
                    "meta", Map.of("lastUpdated", now),
                    "identifier", Map.of(
                            "system", "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-" + dto.getEstabelecimentoSaude(),
                            "value", dto.getIdentificadorIndividuo()
                    ),
                    "type", "document",
                    "timestamp", now,
                    "entry", List.of(
                            Map.of(
                                    "fullUrl", "urn:uuid:transient-0",
                                    "resource", buildCompositionResource(dto, now)
                            ),
                            Map.of(
                                    "fullUrl", "urn:uuid:transient-1",
                                    "resource", buildImmunizationResource(dto)
                            )
                    )
            );

            return objectMapper.writeValueAsString(bundle);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar payload RIA para JSON", e);
        }
    }

    private CompositionResource buildCompositionResource(RecordDTO dto, String now) {
        return CompositionResource.builder()
                .resourceType("Composition")
                .meta(CompositionResource.Meta.builder()
                        .profile(List.of("http://www.saude.gov.br/fhir/r4/StructureDefinition/BRRegistroImunobiologicoAdministradoRotina-1.0"))
                        .build())
                .status("final")
                .type(CompositionResource.Type.builder()
                        .coding(List.of(CompositionResource.Coding.builder()
                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoDocumento")
                                .code("RIA")
                                .build()))
                        .build())
                .subject(CompositionResource.Subject.builder()
                        .identifier(CompositionResource.Identifier.builder()
                                .system("http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0")
                                .value(dto.getIdentificadorIndividuo())
                                .build())
                        .build())
                .date(now)
                .author(List.of(
                        CompositionResource.Author.builder()
                                .identifier(CompositionResource.Identifier.builder()
                                        .system("http://www.saude.gov.br/fhir/r4/StructureDefinition/BREstabelecimentoSaude-1.0")
                                        .value(dto.getEstabelecimentoSaude())
                                        .build())
                                .build()))
                .title("Registro de Imunobiologico Administrado na Rotina")
                .section(List.of(
                        CompositionResource.Section.builder()
                                .entry(List.of(
                                        CompositionResource.EntryRef.builder()
                                                .reference("urn:uuid:transient-1")
                                                .build()))
                                .build()))
                .build();
    }

    private ImmunizationResource buildImmunizationResource(RecordDTO dto) {
        return ImmunizationResource.builder()
                .resourceType("Immunization")
                .meta(ImmunizationResource.Meta.builder()
                        .profile(List.of("http://www.saude.gov.br/fhir/r4/StructureDefinition/BRImunobiologicoAdministrado-2.0"))
                        .build())
                .status("completed")
                .vaccineCode(ImmunizationResource.VaccineCode.builder()
                        .coding(List.of(ImmunizationResource.Coding.builder()
                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BRImunobiologico")
                                .code(dto.getImunobiologico())
                                .build()))
                        .build())
                .patient(ImmunizationResource.Patient.builder()
                        .identifier(CompositionResource.Identifier.builder()
                                .system("http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0")
                                .value(dto.getIdentificadorIndividuo())
                                .build())
                        .build())
                .occurrenceDateTime(dto.getDataImunizacao())
                .manufacturer(ImmunizationResource.Manufacturer.builder()
                        .identifier(CompositionResource.Identifier.builder()
                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BRFabricantePNI")
                                .value(dto.getFabricante())
                                .build())
                        .build())
                .lotNumber(dto.getLote())
                .site(ImmunizationResource.Site.builder()
                        .coding(List.of(ImmunizationResource.Coding.builder()
                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BRLocalAplicacao")
                                .code(dto.getLocalAplicacao())
                                .build()))
                        .build())
                .route(ImmunizationResource.Route.builder()
                        .coding(List.of(ImmunizationResource.Coding.builder()
                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BRViaAdministracao")
                                .code(dto.getViaAdministracao())
                                .build()))
                        .build())
                .performer(List.of(
                        ImmunizationResource.Performer.builder()
                                .actor(ImmunizationResource.Actor.builder()
                                        .reference("Practitioner/" + dto.getIdentificadorProfissional())
                                        .build())
                                .build()))
                .protocolApplied(List.of(
                        ImmunizationResource.ProtocolApplied.builder()
                                .doseNumberString(dto.getDose())
                                .extension(List.of(
                                        ImmunizationResource.Extension.builder()
                                                .url("http://www.saude.gov.br/fhir/r4/StructureDefinition/BREstrategiaVacinacao-1.0")
                                                .valueCodeableConcept(ImmunizationResource.ValueCodeableConcept.builder()
                                                        .coding(List.of(ImmunizationResource.Coding.builder()
                                                                .system("http://www.saude.gov.br/fhir/r4/CodeSystem/BREstrategiaVacinacao")
                                                                .code(dto.getEstrategia())
                                                                .build()))
                                                        .build())
                                                .build(),
                                        ImmunizationResource.Extension.builder()
                                                .url("http://www.saude.gov.br/fhir/r4/StructureDefinition/BREstrategiaVacinacaoPesquisa-1.0")
                                                .extension(List.of(
                                                        ImmunizationResource.SubExtension.builder()
                                                                .url("numeroProtocoloEstudoANVISA")
                                                                .valueString(dto.getAnvisaProtocoloEstudo())
                                                                .build(),
                                                        ImmunizationResource.SubExtension.builder()
                                                                .url("numeroVersaoProtocoloEstudo")
                                                                .valueString(dto.getAnvisaProtocoloVersao())
                                                                .build(),
                                                        ImmunizationResource.SubExtension.builder()
                                                                .url("numeroRegistroVacinaAnvisa")
                                                                .valueString(dto.getAnvisaNumRegistro())
                                                                .build()))
                                                .build()))
                                .build()))
                .build();
    }
}