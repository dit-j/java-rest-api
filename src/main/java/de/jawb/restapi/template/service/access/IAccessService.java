package de.jawb.restapi.template.service.access;

import org.springframework.context.event.EventListener;

import de.jawb.restapi.template.model.access.ApiAccess;
import de.jawb.restapi.template.service.events.AppStartedEvent;

public interface IAccessService {

    @EventListener
    void onStart(AppStartedEvent event);

    ApiAccess getValidApiAccess(String key);

    boolean isValidKey(String key);

    String createNewAccess(String key);

    Long incrementRequestCount(String key);
}
