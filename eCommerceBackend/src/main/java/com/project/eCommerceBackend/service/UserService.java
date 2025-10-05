package com.project.eCommerceBackend.service;

import com.project.eCommerceBackend.api.model.LoginBody;
import com.project.eCommerceBackend.api.model.RegistrationBody;
import com.project.eCommerceBackend.exception.UserAlreadyExistException;
import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.dao.LocalUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    LocalUserDAO localUserDAO;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    JWTService jwtService;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() || localUserDAO.findByUserNameIgnoreCase(registrationBody.getUserName()).isPresent()) {
            throw new UserAlreadyExistException();
        } else {
            LocalUser user = new LocalUser();
            user.setUsername(registrationBody.getUserName());
            user.setFirstName(registrationBody.getFirstName());
            user.setLastName(registrationBody.getLastName());
            user.setEmail(registrationBody.getEmail());
            user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
            return localUserDAO.save(user);
        }
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserDAO.findByUserNameIgnoreCase(loginBody.getUserName());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }

}
