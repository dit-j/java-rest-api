/**
 *
 */
package de.jawb.restapi.template.service.user;

import org.springframework.context.event.EventListener;

import de.jawb.restapi.template.controller.api.response.APIResponse;
import de.jawb.restapi.template.service.events.AppStartedEvent;

/**
 * @author dit (14.12.2015)
 */
public interface IUserService {

    @EventListener
    void onStart(AppStartedEvent event);

    APIResponse saveNewUser(String name, String secondName);

    APIResponse findUserWithId(Long id);

}
