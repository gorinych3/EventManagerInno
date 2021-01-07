package ru.inno.projects.config;

import freemarker.template.TemplateModelException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.InvitationRepo;
import ru.inno.projects.repos.UserRepo;
import ru.inno.projects.services.InvitationService;

import javax.annotation.PostConstruct;


@Configuration
public class FreeMarkerConfig {


    private final InvitationService invitationService;

    private final freemarker.template.Configuration configuration;

    public FreeMarkerConfig(InvitationService invitationService, freemarker.template.Configuration configuration) {

        this.invitationService = invitationService;
        this.configuration = configuration;
    }

    @PostConstruct
    public void setVariableConfiguration() throws TemplateModelException {
        configuration.setSharedVariable("invitationService", invitationService);
    }
}
