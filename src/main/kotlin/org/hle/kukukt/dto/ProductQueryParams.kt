package org.hle.kukukt.dto

import org.hle.kukukt.constant.ProductCategory

class ProductQueryParams(
    val category: ProductCategory?,
    val search: String?,
    val orderBy: String,
    val sort: String
) {
}