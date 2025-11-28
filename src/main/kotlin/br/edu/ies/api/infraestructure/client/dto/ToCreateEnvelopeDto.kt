package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ToCreateEnvelopeDto(
    @field:JsonProperty("title")
    @field:NotBlank
    val documentTitle: String,
    @field:NotNull
    @field:JsonProperty("meta")
    val meta: MetaOptionsDto,
    @field:JsonProperty("recipients")
    @field:NotNull
    @field:Valid
    @field:Size(min = 4, max = 4)
    val assignors: List<ToCreateAssignorDto>
) {
    @field:JsonProperty("type")
    val envelopeType = "DOCUMENT"

    class MetaOptionsDto(
        @field:NotBlank
        @field:JsonProperty("subject")
        val subject: String,
        @field:NotBlank
        @field:JsonProperty("message")
        val message: String,
    ) {
        @field:JsonProperty("dateFormat")
        val dateFormat = "dd/MM/yyyy"

        @field:JsonProperty("distributionMethod")
        val distributionMethod = "EMAIL"

        @field:JsonProperty("allowDictateNextSigner")
        val allowDictateNextSigner = false

        @field:JsonProperty("signingOrder")
        val signingOrder: String = "SEQUENTIAL"
    }
}
