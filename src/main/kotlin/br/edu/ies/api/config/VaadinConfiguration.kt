package br.edu.ies.api.config

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.AppShellSettings
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import org.springframework.context.annotation.Configuration

@Theme("ies-style")
@PWA(
    name = "Portal de Submissão de Trabalhos de Conclusão de Curso",
    shortName = "Portal de Submissão de TCC",
)
@Configuration
class VaadinConfiguration : AppShellConfigurator {
    override fun configurePage(settings: AppShellSettings) {
        settings.addFavIcon("icon", "icon-32x32.png", "image/x-icon")
        super.configurePage(settings)
    }
}