package org.hle.kukukt.service

import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product

interface ProductService {
    fun getProducts(): List<Product>
    fun getProductById(productId: Int): Product?
    fun createProduct(productRequest: ProductRequest): Int?
    fun updateProduct(productId: Int, productRequest: ProductRequest)
}