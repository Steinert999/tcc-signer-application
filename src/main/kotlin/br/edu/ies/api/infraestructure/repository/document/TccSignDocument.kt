package br.edu.ies.api.infraestructure.repository.document

import br.edu.ies.api.infraestructure.client.dto.DocumentDetailsDto
import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDateTime

@Document(collection = "tcc-signs")
data class TccSignDocument(
    @Field(name = "title")
    val title: String,

    @Field(name = "status", targetType = FieldType.STRING)
    var status: SignStatus,

    @Field(name = "submitted_at")
    val submittedAt: LocalDateTime = LocalDateTime.now(),

    @Field(name = "signed_at", write = Field.Write.NON_NULL)
    var signedAt: LocalDateTime? = null,

    @Field(name = "rejected_at", write = Field.Write.NON_NULL)
    var rejectedAt: LocalDateTime? = null,

    @Field(name = "rejected_reason", write = Field.Write.NON_NULL)
    var rejectedReason: String? = null,

    @Field(name = "student")
    val student: StudentDocument,

    @Field(name = "signers")
    val signers: List<SignerDocument>,

    @Field(name = "documenso_envelope_id")
    val documensoEnvelopeId: String
) {
    @Id
    lateinit var id: ObjectId

    fun isSame(detailsDto: DocumentDetailsDto) = detailsDto.envelopeId == documensoEnvelopeId

}