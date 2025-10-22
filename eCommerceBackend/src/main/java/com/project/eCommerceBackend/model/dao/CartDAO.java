package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.Cart;
import com.project.eCommerceBackend.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartDAO extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUser(LocalUser user);
}
