package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreatedEnvelopeDto(
    @field:JsonProperty("id")
    val envelopeId: String
)