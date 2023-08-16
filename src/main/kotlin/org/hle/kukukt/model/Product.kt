package org.hle.kukukt.model

import org.hle.kukukt.constant.ProductCategory
import java.util.Date

class Product(
    val productId: Int,
    val productName: String,
    val category: ProductCategory,
    val imageUrl: String,
    val price: Int,
    val stock: Int,
    val description: String?,
    val createdDate: Date,
    val lastModifiedDate: Date
    )