package com.project.eCommerceBackend.service;

import com.project.eCommerceBackend.api.model.LoginBody;
import com.project.eCommerceBackend.api.model.PasswordResetBody;
import com.project.eCommerceBackend.api.model.RegistrationBody;
import com.project.eCommerceBackend.exception.EmailFailureException;
import com.project.eCommerceBackend.exception.EmailNotFoundException;
import com.project.eCommerceBackend.exception.UserAlreadyExistException;
import com.project.eCommerceBackend.exception.UserNotVerifiedException;
import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.VerificationToken;
import com.project.eCommerceBackend.model.dao.LocalUserDAO;
import com.project.eCommerceBackend.model.dao.VerificationTokenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    LocalUserDAO localUserDAO;

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    JWTService jwtService;

    @Autowired
    private EmailService emailService;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailureException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() || localUserDAO.findByUserNameIgnoreCase(registrationBody.getUserName()).isPresent()) {
            throw new UserAlreadyExistException();
        } else {
            LocalUser user = new LocalUser();
            user.setUsername(registrationBody.getUserName());
            user.setFirstName(registrationBody.getFirstName());
            user.setLastName(registrationBody.getLastName());
            user.setEmail(registrationBody.getEmail());
            user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
            VerificationToken verificationToken = createVerificationToken(user);
            emailService.sendVerificationEmail(verificationToken);
            return localUserDAO.save(user);
        }
    }

    private VerificationToken createVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByUserNameIgnoreCase(loginBody.getUserName());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() == 0 || verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw new EmailNotFoundException();
        }
    }

    public void resetPassword(PasswordResetBody body) {
        String email = jwtService.getResetPasswordEmail(body.getToken());
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            user.setPassword(encryptionService.encryptPassword(body.getPassword()));
            localUserDAO.save(user);
        }
    }
}
