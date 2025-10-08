package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
