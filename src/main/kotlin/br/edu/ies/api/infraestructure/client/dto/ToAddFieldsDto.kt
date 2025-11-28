package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ToAddFieldsDto(
    @field:NotNull
    @field:Positive
    @field:JsonProperty("positionY")
    val y: Int,
) {

    @field:JsonProperty("identifier")
    val identifier = 0

    @field:JsonProperty("type")
    val type = "SIGNATURE"

    @field:JsonProperty("page")
    val page = 3

    @field:JsonProperty("positionX")
    val x = 45

    @field:JsonProperty("width")
    val width = 13

    @field:JsonProperty("height")
    val height = 3
}
