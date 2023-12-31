package org.hle.kukukt.service.impl

import org.hle.kukukt.dao.ProductDao
import org.hle.kukukt.dto.ProductQueryParams
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(val productDao: ProductDao) : ProductService {
    override fun getProducts(queryParam: ProductQueryParams): List<Product> {
        return productDao.getProducts(queryParam)
    }

    override fun countProduct(queryParam: ProductQueryParams): Int {
        return productDao.countProduct(queryParam)
    }

    override fun getProductById(productId: Int): Product? {
        return productDao.getProductById(productId)
    }

    override fun createProduct(productRequest: ProductRequest): Int? {
        return productDao.createProduct(productRequest)
    }

    override fun updateProduct(productId: Int, productRequest: ProductRequest) {
        productDao.updateProduct(productId, productRequest)
    }

    override fun deleteProductById(productId: Int) {
        productDao.deleteProductById(productId)
    }
}