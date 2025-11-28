package br.edu.ies.api.controller.dto

import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import java.time.LocalDateTime

class DocumentStatusDetailsResponse(
    val title: String,
    val status: SignStatus,
    val submittedAt: LocalDateTime,
    val rejectionReason: String?,
    val rejectedAt: LocalDateTime?,
    val signedAt: LocalDateTime?
)
