package org.hle.kukukt.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hle.kukukt.constant.ProductCategory

class ProductRequest(
    @field:NotBlank val productName: String,
    @field:NotNull val category: ProductCategory,
    @field:NotBlank val imageUrl: String,
    @field:NotNull val price: Int,
    @field:NotNull val stock: Int,
    val description: String?
)