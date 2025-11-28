package br.edu.ies.api.business.scheduler

import br.edu.ies.api.business.service.SendToSignService
import br.edu.ies.api.infraestructure.repository.TccSignRepository
import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PrepareToSignScheduler(
    val tccSignRepository: TccSignRepository,
    val sendToSign: SendToSignService
) {

    private val logger = LoggerFactory.getLogger(PrepareToSignScheduler::class.java)

    @Scheduled(cron = "\${scheduler.prepare-to-sign.cron}")
    operator fun invoke() {
        logger.info("Running preparation to sign scheduler")
        tccSignRepository.findAllCreatedDocuments()
            .onEach { document ->
                runCatching {
                    logger.info("Preparing document ${document.id} to be sent to sign")
                    sendToSign(document.documensoEnvelopeId)
                }.fold(
                    onSuccess = {
                        document.status = SignStatus.WAITING_SIGNATURES
                        tccSignRepository.save(document)
                    },
                    onFailure = {
                        logger.error("Error preparing document ${document.id} to be sent to sign", it)
                        document.status = SignStatus.ERROR
                        tccSignRepository.save(document)
                    })
            }
        logger.info("Finished preparation to sign scheduler")
    }
}