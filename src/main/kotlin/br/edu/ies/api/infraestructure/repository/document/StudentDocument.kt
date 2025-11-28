package br.edu.ies.api.infraestructure.repository.document

import org.springframework.data.mongodb.core.mapping.Field

data class StudentDocument(
    @Field(name = "name")
    val name: String,
    @Field(name = "email")
    val email: String
)
