package de.jawb.restapi.template.service.events;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author dit
 *
 */
public class AppStartedEvent extends ApplicationEvent {

    public AppStartedEvent(ApplicationContext ctx) {
        super(ctx);
    }

}
