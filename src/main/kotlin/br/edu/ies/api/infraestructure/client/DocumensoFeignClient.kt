package br.edu.ies.api.infraestructure.client

import br.edu.ies.api.business.validator.IsPDF
import br.edu.ies.api.infraestructure.client.dto.CreatedEnvelopeDto
import br.edu.ies.api.infraestructure.client.dto.DocumentDetailsDto
import br.edu.ies.api.infraestructure.client.dto.ToCreateEnvelopeDto
import br.edu.ies.api.infraestructure.client.dto.ToDistributeEnvelopeDto
import br.edu.ies.api.infraestructure.module.DocumensoPage
import jakarta.validation.Valid
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@FeignClient(name = "documenso-client")
interface DocumensoFeignClient {

    @PostMapping(
        value = ["/envelope/create"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createEnvelope(
        @RequestPart("payload") @Valid toCreateEnvelopeDto: ToCreateEnvelopeDto,
        @RequestPart("files", required = false) @Valid @IsPDF files: Array<MultipartFile>,
    ): CreatedEnvelopeDto

    @PostMapping(
        value = ["/envelope/distribute"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun distributeEnvelope(@Valid @RequestBody toDistributeDocumentDto: ToDistributeEnvelopeDto)

    @GetMapping(
        value = ["document"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        params = [
            "source=DOCUMENT",
            "status=COMPLETED",
        ]
    )
    fun getCompletedDocuments(
        @RequestParam("page") page: Int = 1,
        @RequestParam("perPage") pageSize: Int = 100
    ): DocumensoPage<DocumentDetailsDto>

    @GetMapping(
        value = ["document"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        params = [
            "source=DOCUMENT",
            "status=REJECTED"
        ]
    )
    fun getRejectedDocuments(
        @RequestParam("page") page: Int = 1,
        @RequestParam("perPage") pageSize: Int = 100
    ): DocumensoPage<DocumentDetailsDto>
}