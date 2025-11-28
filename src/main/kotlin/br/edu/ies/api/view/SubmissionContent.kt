package br.edu.ies.api.view

import br.edu.ies.api.business.service.EnvelopeId
import br.edu.ies.api.controller.DocumentController
import br.edu.ies.api.controller.dto.SendToSignDocumentRequest
import br.edu.ies.api.view.infra.VaadinMultipartFile
import br.edu.ies.api.view.submission.AdvisorFields
import br.edu.ies.api.view.submission.CommiteeFields
import br.edu.ies.api.view.submission.StudentFields
import br.edu.ies.api.view.utils.minus
import com.vaadin.flow.component.ComponentUtil
import com.vaadin.flow.component.ScrollOptions
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.NativeLabel
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.UploadI18N
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.server.streams.UploadHandler
import com.vaadin.hilla.parser.plugins.transfertypes.MultipartFileUsageException
import jakarta.annotation.PostConstruct
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile


@RouteAlias(SUBMISSION_ROUTE, layout = TccMainLayout::class)
@Route("", layout = TccMainLayout::class)
@PageTitle("Submissão de TCC")
@Menu(order = 0.0, icon = "vaadin:clipboard-check", title = "Submissão TCC")
class SubmissionContent() : VerticalLayout(), AfterNavigationObserver {

    @Autowired
    private lateinit var controller: DocumentController

    private val signerBinders = mutableListOf<Binder<SendToSignDocumentRequest.SignerRequest>>()
    private val studentBinder = BeanValidationBinder(SendToSignDocumentRequest.StudentRequest::class.java)
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    private var multipartFile: MultipartFile? = null


    @PostConstruct
    fun initView() {
        UI.getCurrent().page.executeJs("window.scrollTo({ top: 0, left:0 , behavior: 'smooth'});");
        className = "content"
        val contentHeader = Div() - {
            className = "content-header"
            val title = H1("Submissão de Trabalho de Conclusão de Curso")
            val subtitle = H2("Preencha o formulário abaixo para enviar seu TCC")
            val uploadFile = Upload() - {
                dropLabel = NativeLabel("Ou arraste aqui seu arquivo")
                isDropAllowed = true
                isAutoUpload = true
                i18n = UploadI18N().apply {
                    addFiles = UploadI18N.AddFiles().setOne("Selecione seu arquivo")
                    maxFiles = 1
                }
                setAcceptedFileTypes(MediaType.APPLICATION_PDF_VALUE, ".pdf")
                setUploadHandler(UploadHandler.inMemory { metadata, data ->
                    multipartFile = VaadinMultipartFile(metadata, data)
                })
                addFileRejectedListener {
                    Notification.show(it.errorMessage, 5000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR)

                }
            }
            add(title, subtitle, uploadFile)
        }

        val studentFields = StudentFields(studentBinder) - {
        }
        val advisorFields = AdvisorFields(signerBinders)
        val committeeFields = CommiteeFields(signerBinders)

        val sendFormButton = Button("Enviar Trabalho") - {
            className = "send-form-button"
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            addClickListener { _ ->
                runCatching {
                    val sendToSignDocumentRequest = SendToSignDocumentRequest(
                        student = studentBinder.bean,
                        signers = signerBinders.map { it.bean }
                    )
                    if (multipartFile == null || multipartFile!!.isEmpty) {
                        throw MultipartFileUsageException("Nenhum arquivo informado para upload")
                    }
                    val constraintViolations = validator.validate(sendToSignDocumentRequest)
                    if (constraintViolations.isNotEmpty()) {
                        throw ConstraintViolationException(constraintViolations)
                    }

                    val envelopeId = controller.sendSignDocument(sendToSignDocumentRequest, multipartFile!!)
                    UI.getCurrent()
                        .also {
                            ComponentUtil.setData(it, EnvelopeId::class.java, envelopeId)
                            ComponentUtil.setData(it, SendToSignDocumentRequest::class.java, sendToSignDocumentRequest)
                            ComponentUtil.setData(it, MultipartFile::class.java, multipartFile)
                        }
                }.fold(
                    onSuccess = { ui -> ui.navigate(SUBMISSION_SUCCESS_ROUTE) },
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
        val warningDescription = Span(
            VaadinIcon.WARNING.create(),
            Span("Todas as submissões são registradas e você receberá um e-mail de confirmação")
        ) - {
            className = "warning-description"
            element.themeList.add("badge warning")
        }
        add(
            contentHeader,
            studentFields,
            advisorFields,
            committeeFields,
            sendFormButton,
            warningDescription
        )

    }

    override fun afterNavigation(event: AfterNavigationEvent) {
        scrollIntoView(
            ScrollOptions(
                ScrollOptions.Behavior.SMOOTH,
                ScrollOptions.Alignment.START,
                ScrollOptions.Alignment.START
            )
        )
    }
}