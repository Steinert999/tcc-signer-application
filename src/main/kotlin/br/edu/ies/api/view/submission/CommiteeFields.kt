package br.edu.ies.api.view.submission

import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.utils.RequiredTextField
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder

class CommiteeFields(binders: MutableList<Binder<SendToSignDocumentRequest.SignerRequest>>) : Card() {
    init {
        className = "committee-fields"
        title = Div("Banca Examinadora")
        setAriaLabel("Informe os dados dos três professores da banca")
        (1..3).map {
            Card() - {
                val signerRequest = SendToSignDocumentRequest.SignerRequest("", "", "COMMITTEE_MEMBER")
                val committeeMemberName = RequiredTextField(
                    "Nome do professor $it é necessário"
                ) - {
                    label = "Professor $it"
                    placeholder = "Nome Completo"
                    addValueChangeListener {
                        signerRequest.name = it.value
                    }
                }
                val committeeMemberEmail = TeacherEmail("E-mail do Professor $it") - {
                    addValueChangeListener {
                        signerRequest.email = it.value
                    }
                }
                add(committeeMemberName, committeeMemberEmail)
                val binder = BeanValidationBinder(SendToSignDocumentRequest.SignerRequest::class.java)
                binder.bean = signerRequest
                binders.addLast(binder)
            }
        }.onEach { add(it) }
    }
}