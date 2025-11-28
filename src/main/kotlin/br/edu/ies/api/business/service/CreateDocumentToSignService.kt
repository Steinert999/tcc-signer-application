package br.edu.ies.api.business.service

import br.edu.ies.api.business.service.mapper.toCreateEnvelope
import br.edu.ies.api.business.service.mapper.toDocument
import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.infraestructure.client.DocumensoFeignClient
import br.edu.ies.api.infraestructure.repository.TccSignRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

typealias EnvelopeId = String

@Service
@Transactional
class CreateDocumentToSignService(
    val tccSignRepository: TccSignRepository,
    val documensoFeignClient: DocumensoFeignClient,
) {

    private val logger = LoggerFactory.getLogger(CreateDocumentToSignService::class.java)

    operator fun invoke(
        sendToSignDocumentRequest: SendToSignDocumentRequest,
        multipartFile: MultipartFile
    ): EnvelopeId {
        val toCreate = sendToSignDocumentRequest.toCreateEnvelope()
        val createdEnvelopeResponse = documensoFeignClient.createEnvelope(
            toCreate,
            arrayOf(multipartFile)
        ).also { logger.info("Document sent to documenso envelope-id=${it.envelopeId}") }
        val documentToSave = sendToSignDocumentRequest.toDocument(
            envelopeId = createdEnvelopeResponse.envelopeId,
            title = toCreate.documentTitle
        )
        tccSignRepository.save(documentToSave).also {
            logger.info("Document saved on database id=${it.id}")
        }
        return createdEnvelopeResponse.envelopeId
    }
}