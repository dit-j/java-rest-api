/**
 *
 */
package de.jawb.restapi.template.service.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author dit (21.02.2016)
 */
@Component
public class ContextRefreshListener {

    private static final Logger       _logger = LoggerFactory.getLogger(ContextRefreshListener.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ApplicationContext        ctx;


    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        _logger.debug("handleContextRefresh");
        if (event.getApplicationContext() == ctx) {
            publisher.publishEvent(new AppStartedEvent(event.getApplicationContext()));
        }
    }

}
