/**
 *
 */
package de.jawb.restapi.template.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.jawb.restapi.template.controller.api.response.APIResponse;
import de.jawb.restapi.template.model.user.User;
import de.jawb.restapi.template.model.user.UserDao;
import de.jawb.restapi.template.service.events.AppStartedEvent;

/**
 * @author dit (14.12.2015)
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao         _udao;

    @Autowired
    private PasswordEncoder _encoder;

    @Override
    public void onStart(AppStartedEvent event) {}

    @Transactional
    @Override
    public APIResponse saveNewUser(String name, String secondName) {

        User u = new User();
        u.setFirstName(name);
        u.setSecondName(secondName);
        u.setLogin("test");
        u.setPassword(_encoder.encode("12345"));

        _udao.create(u);

        return APIResponse.user(u);
    }

    @Transactional(readOnly = true)
    @Override
    public APIResponse findUserWithId(Long id) {
        return APIResponse.user(_udao.getByID(id));
    }

}
