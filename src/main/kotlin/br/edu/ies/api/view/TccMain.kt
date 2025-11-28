package br.edu.ies.api.view

import com.vaadin.flow.component.HasElement
import com.vaadin.flow.component.html.Main
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.RouterLayout

class TccMainLayout : Main(), RouterLayout {

    private val childWrapper = VerticalLayout()

    init {
        add(Header(), childWrapper, Footer())
    }

    override fun showRouterLayoutContent(content: HasElement) {
        childWrapper.removeAll()
        childWrapper.element.appendChild(content.element)
    }
}