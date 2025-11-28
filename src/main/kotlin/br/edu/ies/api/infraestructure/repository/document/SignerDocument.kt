package br.edu.ies.api.infraestructure.repository.document

import br.edu.ies.api.infraestructure.repository.document.enum.SignerType
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType

data class SignerDocument(
    @Field(name = "name")
    val name: String,
    @Field(name = "email")
    val email: String,
    @Field(name = "type", targetType = FieldType.STRING)
    val type: SignerType
) {
    @Field(name = "id")
    lateinit var id: String
}
