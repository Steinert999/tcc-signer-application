package br.edu.ies.api.view

import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon

class ActionButtons(ui: UI) : Div() {
    init {
        className = "action-buttons"
        val backToHomeButton = Button() - {
            text = "Voltar para a Página Inicial"
            icon = VaadinIcon.HOME_O.create()
            addClickListener {
                ui.navigate(SUBMISSION_ROUTE)
            }
        }
        val trackingSubmissionButton = Button() - {
            text = "Acompanhar Status"
            icon = VaadinIcon.SEARCH.create()
            addClickListener {
                ui.navigate(SUBMISSION_TRACKING_SEARCH_ROUTE)
            }
        }
        add(backToHomeButton, trackingSubmissionButton)
    }
}