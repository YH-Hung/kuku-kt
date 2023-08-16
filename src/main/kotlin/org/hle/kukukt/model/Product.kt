package org.hle.kukukt.model

import java.util.Date

class Product(
    val productId: Int,
    val productName: String,
    val category: String,
    val imageUrl: String,
    val price: Int,
    val stock: Int,
    val description: String?,
    val createdDate: Date,
    val lastModifiedDate: Date
    )