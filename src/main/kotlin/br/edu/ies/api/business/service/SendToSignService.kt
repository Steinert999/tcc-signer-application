package br.edu.ies.api.business.service

import br.edu.ies.api.infraestructure.client.DocumensoFeignClient
import br.edu.ies.api.infraestructure.client.dto.ToDistributeEnvelopeDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SendToSignService(
    val documensoFeignClient: DocumensoFeignClient
) {
    private val logger = LoggerFactory.getLogger(SendToSignService::class.java)

    operator fun invoke(envelopeId: String) {
        documensoFeignClient.distributeEnvelope(ToDistributeEnvelopeDto(envelopeId))
            .also { logger.info("Document send to sign. envelope-id=$envelopeId") }
    }

}
