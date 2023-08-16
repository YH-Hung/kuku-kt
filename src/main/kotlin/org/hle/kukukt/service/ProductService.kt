package org.hle.kukukt.service

import org.hle.kukukt.model.Product

interface ProductService {
    fun getProductById(productId: Int) : Product?
}