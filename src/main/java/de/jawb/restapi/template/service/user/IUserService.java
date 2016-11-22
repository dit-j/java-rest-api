/**
 *
 */
package de.jawb.restapi.template.service.user;

import org.springframework.context.event.EventListener;

import de.jawb.restapi.template.model.user.User;
import de.jawb.restapi.template.service.events.AppStartedEvent;

/**
 * @author dit (14.12.2015)
 */
public interface IUserService {

    @EventListener
    void onStart(AppStartedEvent event);

    User saveNewUser(String name, String secondName);

    Object findUserWithId(Long id);

}
