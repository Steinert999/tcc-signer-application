package br.edu.ies.api.view.submission

import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.utils.RequiredTextField
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.data.binder.Binder

class StudentFields(binder: Binder<SendToSignDocumentRequest.StudentRequest>) : Card() {

    private val studentRequest = SendToSignDocumentRequest.StudentRequest("", "")

    init {
        binder.bean = studentRequest
        title = Div("Dados do Aluno")
        setAriaLabel("Informe seus dados pessoais")
        val studentName = RequiredTextField(
            "Seu nome é necessário"
        ) - {
            label = "Seu Nome Completo"
            addValueChangeListener {
                studentRequest.name = it.value
            }
        }

        val studentEmail = StudentEmail() - {
            addValueChangeListener {
                studentRequest.email = it.value
            }
        }
        add(studentName, studentEmail)
    }

}