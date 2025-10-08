package com.project.eCommerceBackend.service;

import com.project.eCommerceBackend.model.Product;
import com.project.eCommerceBackend.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDao;

    public List<Product> getProducts() {
        return productDao.findAll();
    }
}
