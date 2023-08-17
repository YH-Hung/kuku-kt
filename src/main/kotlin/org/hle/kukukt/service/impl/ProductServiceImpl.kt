package org.hle.kukukt.service.impl

import org.hle.kukukt.dao.ProductDao
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(val productDao: ProductDao) : ProductService {
    override fun getProductById(productId: Int): Product? {
        return productDao.getProductById(productId)
    }

    override fun createProduct(productRequest: ProductRequest): Int? {
        return productDao.createProduct(productRequest)
    }
}