package br.edu.ies.api.view.tracking

import br.edu.ies.api.controller.DocumentController
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import jakarta.validation.ConstraintViolationException

class TrackingSearchCard(val controller: DocumentController) : Card() {
    init {
        className = "tracking-search-card"
        title = H1("Acompanhar Status da Submissão")
        subtitle = H2("Insira o identificador do documento para verificar o status da assinatura.")
        val textField = TextField() - { placeholder = "Ex: envelope_..." }
        val searchButton = Button("Pesquisar") - {
            icon = VaadinIcon.SEARCH.create()
            addClickListener {
                runCatching {
                    val documentId = textField.value
                    controller.getDocumentStatusDetails(documentId)
                }.fold(
                    onSuccess = {
                        findAncestor(VerticalLayout::class.java) - {
                            children.filter { child -> child is TrackingDetails }
                                .forEach { child -> child.removeFromParent() }
                            add(TrackingDetails(it))
                        }
                    },
                    onFailure = {
                        when (it) {
                            is ConstraintViolationException ->
                                it.constraintViolations.onEach { violation ->
                                    Notification.show(violation.message)
                                        .addThemeVariants(NotificationVariant.LUMO_ERROR)
                                }

                            else -> Notification.show("Um erro aconteceu: ${it.localizedMessage}")
                                .addThemeVariants(NotificationVariant.LUMO_ERROR)
                        }
                    }
                )
            }
        }
        add(textField, searchButton)
    }
}