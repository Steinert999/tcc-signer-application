package br.edu.ies.api.business.service.mapper

import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.infraestructure.client.dto.ToAddFieldsDto
import br.edu.ies.api.infraestructure.client.dto.ToCreateAssignorDto
import br.edu.ies.api.infraestructure.client.dto.ToCreateEnvelopeDto
import br.edu.ies.api.infraestructure.repository.document.SignerDocument
import br.edu.ies.api.infraestructure.repository.document.StudentDocument
import br.edu.ies.api.infraestructure.repository.document.TccSignDocument
import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import br.edu.ies.api.infraestructure.repository.document.enum.SignerType


fun SendToSignDocumentRequest.toDocument(envelopeId: String, title: String) = TccSignDocument(
    title = title,
    status = SignStatus.CREATED,
    student = student.toDocument(),
    signers = signers.map { it.toDocument() },
    documensoEnvelopeId = envelopeId
)

private fun SendToSignDocumentRequest.SignerRequest.toDocument() = SignerDocument(
    name = name,
    email = email,
    type = SignerType.valueOf(type)
)

private fun SendToSignDocumentRequest.StudentRequest.toDocument() = StudentDocument(
    name = name,
    email = email
)

fun SendToSignDocumentRequest.toCreateEnvelope() = ToCreateEnvelopeDto(
    documentTitle = "TCC - ${student.name} - ${student.email}",
    meta = toMetaOptions(),
    assignors = listOf(
        student.generateAssignorDto(),
        *signers.mapIndexed { index, signerDto -> signerDto.toCreateAssignorDto(index) }.toTypedArray()
    ).sortedBy { it.signingOrder }
)

fun SendToSignDocumentRequest.toMetaOptions() = ToCreateEnvelopeDto.MetaOptionsDto(
    subject = "TCC para assinatura - ${student.name}",
    message = """
        |Prezados professores, favor realizar a assinatura do Trabalho de Conclusão de Curso 
        |do aluno ${student.name}.
        |Atenciosamente, Instituto de Ensino Superior da Grande Florianópolis
    """.trimMargin()
)


fun SendToSignDocumentRequest.SignerRequest.toCreateAssignorDto(index: Int) = ToCreateAssignorDto(
    name = this.name,
    email = this.email,
    signingOrder = SignerType.valueOf(type).order,
    fields = listOf(generateSignatureFields(index))
)

fun SendToSignDocumentRequest.StudentRequest.generateAssignorDto() = ToCreateAssignorDto(
    name = name,
    email = email,
    signingOrder = 0,
    role = "CC"
)

fun SendToSignDocumentRequest.SignerRequest.generateSignatureFields(index: Int) = SignerType.valueOf(type).let {
    when (it) {
        SignerType.ADVISOR -> 61
        SignerType.COMMITTEE_MEMBER -> 61 + (10 * index)
    }.let(::ToAddFieldsDto)
}
