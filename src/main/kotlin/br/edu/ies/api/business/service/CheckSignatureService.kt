package br.edu.ies.api.business.service

import br.edu.ies.api.infraestructure.client.DocumensoFeignClient
import br.edu.ies.api.infraestructure.repository.TccSignRepository
import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class CheckSignatureService(
    val tccSignRepository: TccSignRepository,
    val documensoFeignClient: DocumensoFeignClient
) {

    private val logger = LoggerFactory.getLogger(CheckSignatureService::class.java)

    operator fun invoke() {
        val waitingDocuments = tccSignRepository.findAllWaitingSignatureDocuments()
        val finishedDocumensoDocuments = buildList {
            addAll(documensoFeignClient.getRejectedDocuments().data)
            addAll(documensoFeignClient.getCompletedDocuments().data)
        }
        logger.info("Checking ${waitingDocuments.size} waiting documents statuses")
        waitingDocuments.map { doc ->
            val relatedDocumenso = finishedDocumensoDocuments.firstOrNull(doc::isSame)
            runCatching {
                relatedDocumenso?.let {
                    when (it.status) {
                        "REJECTED" -> doc.apply {
                            status = SignStatus.REJECTED
                            rejectedReason =
                                it.detailsOfRecipients.find { recipient -> recipient.rejectionReason != null }?.rejectionReason
                            rejectedAt = LocalDateTime.now()
                        }

                        "COMPLETED" -> doc.apply {
                            status = SignStatus.SIGNED
                            signedAt = LocalDateTime.now()
                        }

                        else -> doc.apply { status = SignStatus.ERROR }
                    }.let(tccSignRepository::save)
                }
            }
        }.partition { it.isSuccess }
            .let { (successUpdatedDocuments, errorOnUpdateDocuments) ->
                logger.info("Checked successfully ${successUpdatedDocuments.size} documents")
                if (errorOnUpdateDocuments.isNotEmpty()) {
                    errorOnUpdateDocuments.onEach {
                        logger.error("Error on update document: ${it.exceptionOrNull()?.message}")
                    }
                }

            }
    }
}