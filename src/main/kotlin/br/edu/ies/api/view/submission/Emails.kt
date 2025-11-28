package br.edu.ies.api.view.submission

import br.edu.ies.api.view.utils.RequiredEmailField

class TeacherEmail(private val teacherEmailLabel: String) : RequiredEmailField() {
    init {
        label = teacherEmailLabel
        helperText = "exemplo@docente.suafaculdade.com.br"
        errorMessage = "Informe um email de docente válido"
    }
}

class StudentEmail(private val studentEmailLabel: String = "Seu E-mail") : RequiredEmailField() {
    init {
        label = studentEmailLabel
        helperText = "exemplo@aluno.ies.edu.br"
        errorMessage = "Informe um email de estudante válido"
    }
}