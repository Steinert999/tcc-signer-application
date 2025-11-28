package br.edu.ies.api.controller

import br.edu.ies.api.business.service.CreateDocumentToSignService
import br.edu.ies.api.business.service.EnvelopeId
import br.edu.ies.api.business.service.FindDocumentStatusDetailsService
import br.edu.ies.api.business.validator.IsPDF
import br.edu.ies.api.controller.dto.DocumentStatusDetailsResponse
import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

@SpringComponent
@UIScope
class DocumentController(
    private val createDocumentToSign: CreateDocumentToSignService,
    private val findDocumentStatusDetails: FindDocumentStatusDetailsService
) {
    fun sendSignDocument(
        @Valid sendToSignDocumentRequest: SendToSignDocumentRequest,
        @Valid @NotNull @IsPDF pdfFile: MultipartFile
    ) = createDocumentToSign(sendToSignDocumentRequest, pdfFile)

    fun getDocumentStatusDetails(@Valid @NotBlank envelopeId: EnvelopeId): DocumentStatusDetailsResponse =
        findDocumentStatusDetails(envelopeId)
}