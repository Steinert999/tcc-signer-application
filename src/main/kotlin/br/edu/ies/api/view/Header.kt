package br.edu.ies.api.view

import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.HorizontalLayout

class Header : HorizontalLayout() {

    init {
        className = "header"
        val imageHeader = Div()
        val card = Card() - {
            add(H1("Portal de Submissão de Trabalhos de Conclusão de Curso"))
            add(Paragraph("Instituto de Ensino Superior da Grande Florianópolis"))
        }
        add(imageHeader, card, ActionButtons(UI.getCurrent()))
    }
}