package br.edu.ies.api.view

import br.edu.ies.api.controller.DocumentController
import br.edu.ies.api.view.tracking.TrackingSearchCard
import com.vaadin.flow.component.ScrollOptions
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired

@PageTitle("Acompanhamento de TCC")
@Route(SUBMISSION_TRACKING_SEARCH_ROUTE, layout = TccMainLayout::class)
@Menu(order = 0.0, icon = "vaadin:clipboard-check", title = "Submissão TCC")
class TrackingContent : VerticalLayout(), AfterNavigationObserver {

    @Autowired
    private lateinit var controller: DocumentController


    @PostConstruct
    fun initView() {
        className = "tracking-content"
        val searchTrackingCard = TrackingSearchCard(controller)
        add(searchTrackingCard)
    }

    override fun afterNavigation(event: AfterNavigationEvent?) {
        scrollIntoView(
            ScrollOptions(
                ScrollOptions.Behavior.SMOOTH,
                ScrollOptions.Alignment.START,
                ScrollOptions.Alignment.START
            )
        )
    }
}