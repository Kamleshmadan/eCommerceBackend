package com.project.eCommerceBackend.service;

import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.WebOrder;
import com.project.eCommerceBackend.model.dao.WebOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private WebOrderDAO webOrderDao;

    public List<WebOrder> getOrder(LocalUser user) {
        return webOrderDao.findByUser(user);
    }
}
