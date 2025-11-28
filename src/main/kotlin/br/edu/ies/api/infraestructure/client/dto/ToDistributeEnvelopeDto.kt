package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

class ToDistributeEnvelopeDto(
    @field:NotBlank
    @field:JsonProperty("envelopeId")
    val envelopeId: String
)