package org.hle.kukukt.service

import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product

interface ProductService {
    fun getProductById(productId: Int): Product?
    fun createProduct(productRequest: ProductRequest): Int?
    fun updateProduct(productId: Int, productRequest: ProductRequest)
}