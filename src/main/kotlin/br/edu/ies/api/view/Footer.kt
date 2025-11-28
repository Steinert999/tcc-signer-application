package br.edu.ies.api.view

import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.card.Card
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import java.time.Year

class Footer : HorizontalLayout() {
    init {
        className = "footer"
        add(Card() - {
            val copyrightIcon = VaadinIcon.COPYRIGHT.create()
            val copyright =
                Paragraph("${Year.now()} IES - Instituto de Ensino Superior da Grande Florianópolis. Todos os direitos reservados.")
            add(copyrightIcon, copyright)
        })
    }
}