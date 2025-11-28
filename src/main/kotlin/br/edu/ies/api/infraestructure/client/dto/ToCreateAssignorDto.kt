package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ToCreateAssignorDto(
    @field:NotBlank
    @field:JsonProperty("name")
    val name: String,
    @field:NotBlank
    @field:Email
    @field:JsonProperty("email")
    val email: String,
    @field:NotNull
    @field:Positive
    @field:Digits(integer = 10, fraction = 0)
    @field:JsonProperty("signingOrder")
    val signingOrder: Int,
    @field:JsonProperty("role")
    val role: String = "SIGNER",
    @field:JsonProperty("fields")
    val fields: List<ToAddFieldsDto> = listOf()
) {
}
