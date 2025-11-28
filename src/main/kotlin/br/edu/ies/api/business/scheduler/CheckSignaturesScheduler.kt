package br.edu.ies.api.business.scheduler

import br.edu.ies.api.business.service.CheckSignatureService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CheckSignaturesScheduler(
    val checkSignature: CheckSignatureService
) {

    private val logger = LoggerFactory.getLogger(CheckSignaturesScheduler::class.java)


    @Scheduled(cron = "\${scheduler.check-signatures.cron}")
    operator fun invoke() {
        logger.info("Running check signatures scheduler")
        checkSignature()
        logger.info("Finished check signatures scheduler")
    }
}