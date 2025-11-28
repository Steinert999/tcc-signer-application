package br.edu.ies.api.view.utils

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.textfield.TextField

operator fun <T : Component> T.minus(f: T.() -> Unit): T = this.apply(f)


class RequiredTextField(private val onErrorMessage: String) : TextField() {
    init {
        isRequired = true
        errorMessage = onErrorMessage
    }
}

open class RequiredEmailField : TextField() {
    init {
        isRequired = true
    }
}