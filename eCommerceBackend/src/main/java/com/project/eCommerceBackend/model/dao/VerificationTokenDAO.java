package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);

}
