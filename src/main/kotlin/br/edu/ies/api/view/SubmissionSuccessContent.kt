package br.edu.ies.api.view

import br.edu.ies.api.business.service.EnvelopeId
import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.submission.SuccessCard
import br.edu.ies.api.view.submissionSuccess.SuccessInfoCard
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.ComponentUtil
import com.vaadin.flow.component.ScrollOptions
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.markdown.Markdown
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.springframework.web.multipart.MultipartFile

@PageTitle("Submissão de TCC - Sucesso")
@Route(SUBMISSION_SUCCESS_ROUTE, layout = TccMainLayout::class)
@Menu(order = 0.0, icon = "vaadin:clipboard-check", title = "Submissão TCC")
class SubmissionSuccessContent : VerticalLayout(), AfterNavigationObserver {

    private lateinit var envelopeId: EnvelopeId
    private lateinit var sendToSignDocumentRequest: SendToSignDocumentRequest
    private lateinit var multipartFile: MultipartFile

    override fun afterNavigation(event: AfterNavigationEvent) {
        scrollIntoView(
            ScrollOptions(
                ScrollOptions.Behavior.SMOOTH,
                ScrollOptions.Alignment.START,
                ScrollOptions.Alignment.START
            )
        )
        apply {
            className = "submission-success"
        }
        val ui = UI.getCurrent()
        envelopeId = ComponentUtil.getData(ui, EnvelopeId::class.java)
        sendToSignDocumentRequest = ComponentUtil.getData(ui, SendToSignDocumentRequest::class.java)
        multipartFile = ComponentUtil.getData(ui, MultipartFile::class.java)

        val successCard = SuccessCard(envelopeId)
        val successInfoCard = SuccessInfoCard(sendToSignDocumentRequest, multipartFile)

        val actionButtons = ActionButtons(ui)
        val nextStepsTooltip = Markdown(
            """
                ### Próximos Passos
                - **Acompanhar o Status da Assinatura:** Você pode acompanhar o progresso da assinatura do seu TCC acessando a seção de acompanhamento.
                - **Notificações por Email:** Fique atento ao seu email, pois você receberá notificações sobre o status da assinatura.
                - **Contato para Suporte:** Se tiver dúvidas ou precisar de assistência, entre em contato com a ouvidoria acadêmica.
            """.trimIndent()
        ) - { className = "next-steps-tooltip" }
        add(successCard, successInfoCard, actionButtons, nextStepsTooltip)
    }
}