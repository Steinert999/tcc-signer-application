package br.edu.ies.api.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable

data class SendToSignDocumentRequest(
    @field:NotNull
    @field:Valid
    @field:JsonProperty("student")
    var student: StudentRequest,

    @field:Size(min = 4, max = 4)
    @field:JsonProperty("signers")
    @field:Valid
    var signers: List<SignerRequest>,
) : Serializable {
    data class SignerRequest(
        @field:NotBlank
        @field:JsonProperty("name")
        var name: String,

        @field:NotBlank
        @field:Email
        @field:JsonProperty("email")
        var email: String,

        @field:NotBlank
        @field:JsonProperty("type")
        val type: String
    )

    data class StudentRequest(
        @field:NotBlank
        @field:JsonProperty("name")
        var name: String,

        @field:NotBlank
        @field:Email
        @field:JsonProperty("email")
        var email: String
    )
}


