package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.CartItem;
import org.springframework.data.repository.ListCrudRepository;

public interface CartItemDAO extends ListCrudRepository<CartItem, Long> {

}

