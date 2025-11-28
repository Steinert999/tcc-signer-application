package br.edu.ies.api.business.service

import br.edu.ies.api.business.service.mapper.toDocumentStatusDetails
import br.edu.ies.api.controller.dto.DocumentStatusDetailsResponse
import br.edu.ies.api.infraestructure.repository.TccSignRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class FindDocumentStatusDetailsService(
    val tccSignRepository: TccSignRepository
) {

    operator fun invoke(envelopeId: EnvelopeId): DocumentStatusDetailsResponse =
        tccSignRepository.findByDocumensoEnvelopeId(envelopeId)?.toDocumentStatusDetails()
            ?: error("Document with envelope-id=$envelopeId not found")
}
