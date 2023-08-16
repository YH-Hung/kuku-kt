package org.hle.kukukt.dao

import org.hle.kukukt.model.Product

interface ProductDao {

    fun getProductById(productId: Int) : Product?
}