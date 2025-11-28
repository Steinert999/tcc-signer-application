package br.edu.ies.api.view.submission

import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.utils.RequiredTextField
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder

class AdvisorFields(binders: MutableList<Binder<SendToSignDocumentRequest.SignerRequest>>) : Card() {
    val signerRequest = SendToSignDocumentRequest.SignerRequest("", "", "ADVISOR")

    init {
        title = Div("Dados do Orientador")
        setAriaLabel("Informe os dados do seu orientador")
        val advisorName = RequiredTextField(
            "Nome do orientador é necessário"
        ) - {
            label = "Nome do Orientador"
            placeholder = "Nome Completo"
            addValueChangeListener {
                signerRequest.name = it.value
            }
        }
        val advisorEmail = TeacherEmail("E-mail do Orientador") - {
            addValueChangeListener {
                signerRequest.email = it.value
            }
        }
        add(advisorName, advisorEmail)
        val binder = BeanValidationBinder(SendToSignDocumentRequest.SignerRequest::class.java)
        binder.bean = signerRequest
        binders.addLast(binder)
    }
}