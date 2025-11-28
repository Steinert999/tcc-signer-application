package br.edu.ies.api.infraestructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class DocumentDetailsDto(
    @field:JsonProperty("envelopeId")
    val envelopeId: String,
    @field:JsonProperty("status")
    val status: String,
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("createdAt")
    val createdAt: Instant,
    @field:JsonProperty("updatedAt")
    val updatedAt: Instant,
    @field:JsonProperty("completedAt")
    val completedAt: Instant?,
    @field:JsonProperty("deletedAt")
    val deletedAt: Instant?,
    @field:JsonProperty("recipients")
    val detailsOfRecipients: List<RecipientDetailsDto>
)
