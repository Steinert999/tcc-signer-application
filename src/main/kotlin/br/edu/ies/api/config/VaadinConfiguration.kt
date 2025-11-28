package br.edu.ies.api.config

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import org.springframework.context.annotation.Configuration

@Theme("ies-style")
@PWA(
    name = "Portal de Submissão de Trabalhos de Conclusão de Curso",
    shortName = "Portal de Submissão de TCC",
)
@Configuration
class VaadinConfiguration : AppShellConfigurator