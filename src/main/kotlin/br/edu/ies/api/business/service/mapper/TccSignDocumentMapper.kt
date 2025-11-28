package br.edu.ies.api.business.service.mapper

import br.edu.ies.api.controller.dto.DocumentStatusDetailsResponse
import br.edu.ies.api.infraestructure.repository.document.TccSignDocument


fun TccSignDocument.toDocumentStatusDetails() = DocumentStatusDetailsResponse(
    title = this.title,
    status = this.status,
    rejectedAt = this.rejectedAt,
    rejectionReason = this.rejectedReason,
    submittedAt = this.submittedAt,
    signedAt = this.signedAt,
)