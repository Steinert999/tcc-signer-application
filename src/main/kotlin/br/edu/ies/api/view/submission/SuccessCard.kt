package br.edu.ies.api.view.submission

import br.edu.ies.api.business.service.EnvelopeId
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.VaadinIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SuccessCard(val value: EnvelopeId) : Card() {
    init {
        className = "success-card"
        val successIcon = VaadinIcon.CHECK_CIRCLE_O.create()
        val title = H1("Submissão realizada com sucesso!")
        val subtitle = Paragraph("Seu documento foi recebido e registrado para assinatura.")
        val documentCard = DocumentCard(value)
        add(successIcon, title, subtitle, documentCard)
    }

    class DocumentCard(val envelopeId: EnvelopeId) : Card() {
        init {
            className = "document-card"
            val documentCardTitle = H1("Identificador do documento")
            val documentCardContent = Div() - {
                val documentCardId = Paragraph(envelopeId)
                val copyButton = Button() - {
                    icon = VaadinIcon.COPY_O.create()
                    addClickListener {
                        this.element.executeJs(
                            "addEventListener('click', e => navigator.clipboard.writeText($0))", envelopeId
                        );
                    }
                }
                add(documentCardId, copyButton)
            }
            val creationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            val documentCardCreationDate = Paragraph(creationDateTime) - { className = "creation-date" }
            add(documentCardTitle, documentCardContent, documentCardCreationDate)
        }
    }
}