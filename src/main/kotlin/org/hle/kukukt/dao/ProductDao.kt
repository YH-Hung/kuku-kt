package org.hle.kukukt.dao

import org.hle.kukukt.dto.ProductQueryParams
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product

interface ProductDao {
    fun getProducts(queryParam: ProductQueryParams): List<Product>
    fun countProduct(queryParam: ProductQueryParams): Int

    fun getProductById(productId: Int): Product?

    fun createProduct(productRequest: ProductRequest): Int?

    fun updateProduct(productId: Int, productRequest: ProductRequest)

    fun deleteProductById(productId: Int)
}