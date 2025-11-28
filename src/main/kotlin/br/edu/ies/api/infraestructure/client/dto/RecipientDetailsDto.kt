package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RecipientDetailsDto(
    @field:JsonProperty("role")
    val role: String,
    @field:JsonProperty("readStatus")
    val readStatus: String,
    @field:JsonProperty("signingStatus")
    val signingStatus: String,
    @field:JsonProperty("sendStatus")
    val sendStatus: String,
    @field:JsonProperty("email")
    val email: String,
    @field:JsonProperty("name")
    val name: String,
    @field:JsonProperty("rejectionReason")
    val rejectionReason: String?
)