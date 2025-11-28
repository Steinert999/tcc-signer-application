package br.edu.ies.api.view.tracking

import br.edu.ies.api.controller.dto.DocumentStatusDetailsResponse
import br.edu.ies.api.infraestructure.repository.document.enum.SignStatus
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.Span
import java.time.format.DateTimeFormatter

class TrackingDetails(val details: DocumentStatusDetailsResponse) : Card() {
    private val defaultFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    init {
        className = "tracking-details"
        title = H1("Detalhes da submissão")
        val name = H2(details.title)
        val dynamicComponent = dynamicComponent(details)
        val submissionDate =
            Paragraph("Criado em: ${details.submittedAt.format(defaultFormat)}")
        add(name, dynamicComponent, submissionDate)
    }

    fun dynamicComponent(details: DocumentStatusDetailsResponse): Component = when (details.status) {
        SignStatus.SIGNED -> {
            Card() - {
                title = Span("Assinado") - { element.themeList.add("badge success") }
                subtitle = H3(details.signedAt!!.format(defaultFormat))
            }
        }

        SignStatus.REJECTED -> {
            Card() - {
                title = Span("Documento Rejeitado") - { element.themeList.add("badge error") }
                subtitle = H3("Rejeitado em: ${details.rejectedAt!!.format(defaultFormat)}")
                val reason = Paragraph("Razão: ${details.rejectionReason!!}")
                add(reason)
            }
        }

        SignStatus.WAITING_SIGNATURES -> {
            Span("Aguardando assinaturas") - { element.themeList.add("badge warning") }
        }

        SignStatus.ERROR -> Span("Erro") - { element.themeList.add("badge error") }
        SignStatus.CREATED -> Span("Criado") - {
            element.themeList.add("badge contrast")
        }
    }
}