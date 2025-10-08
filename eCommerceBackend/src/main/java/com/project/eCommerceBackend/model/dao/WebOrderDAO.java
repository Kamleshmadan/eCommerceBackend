package com.project.eCommerceBackend.model.dao;

import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}
