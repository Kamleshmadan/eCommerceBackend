package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocalUserDAO extends CrudRepository<LocalUser, Long> {

    Optional<LocalUser> findByUserNameIgnoreCase(String userName);

    Optional<LocalUser> findByEmailIgnoreCase(String email);

}
