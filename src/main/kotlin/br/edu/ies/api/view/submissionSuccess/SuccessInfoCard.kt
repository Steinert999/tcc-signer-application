package br.edu.ies.api.view.submissionSuccess

import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import org.springframework.web.multipart.MultipartFile

class SuccessInfoCard(val sendToSignDocumentRequest: SendToSignDocumentRequest, val file: MultipartFile) : Div() {

    init {

        className = "success-info-card"
        add(
            FileInfoCard(file),
            StudentInfoCard(sendToSignDocumentRequest.student),
            AdvisorInfoCard(sendToSignDocumentRequest.signers.first { it.type == "ADVISOR" }),
            CommitteeMembersInfoCard(sendToSignDocumentRequest.signers.filter { it.type == "COMMITTEE_MEMBER" })
        )
    }

    class FileInfoCard(val file: MultipartFile) : Card() {
        init {
            title = H1("Documento Enviado")
            headerPrefix = VaadinIcon.FILE_TEXT_O.create()
            val fileCard = Card() - {
                title = H1(file.name)
                subtitle = Paragraph("${file.size} bytes")
            }
            add(fileCard)
        }
    }

    class StudentInfoCard(student: SendToSignDocumentRequest.StudentRequest) : Card() {
        init {
            title = H1("Dados do Aluno")
            headerPrefix = VaadinIcon.USER.create()
            val nameField = TextField("Nome") - {
                value = student.name
                isReadOnly = true
                addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
            }
            val emailField = EmailField("E-mail") - {
                value = student.email
                isReadOnly = true
                addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
            }
            add(nameField, emailField)
        }
    }

    class AdvisorInfoCard(advisor: SendToSignDocumentRequest.SignerRequest) : Card() {
        init {
            title = H1("Orientador")
            headerPrefix = VaadinIcon.USER.create()
            val nameField = TextField("Nome") - {
                value = advisor.name
                isReadOnly = true
                addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
            }
            val emailField = EmailField("E-mail") - {
                value = advisor.email
                isReadOnly = true
                addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
            }
            add(nameField, emailField)
        }
    }

    class CommitteeMembersInfoCard(members: List<SendToSignDocumentRequest.SignerRequest>) : Card() {
        init {
            title = H1("Banca Examinadora")
            headerPrefix = VaadinIcon.USERS.create()
            val committeeMemberFields = members.mapIndexed { index, signer ->
                Card() - {
                    title = H1("Professor ${index + 1}")
                    val committeeMemberNameField = TextField("Nome") - {
                        value = signer.name
                        isReadOnly = true
                        addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
                    }
                    val commiteeMemberEmailField = EmailField("E-mail") - {
                        value = signer.email
                        isReadOnly = true
                        addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER)
                    }
                    add(committeeMemberNameField, commiteeMemberEmailField)
                }
            }
            add(*committeeMemberFields.toTypedArray())
        }
    }
}