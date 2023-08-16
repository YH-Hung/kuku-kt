package org.hle.kukukt.controller

import org.hle.kukukt.model.Product
import org.hle.kukukt.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productService: ProductService) {

    @GetMapping("/products/{productId}")
    fun getProduct(@PathVariable productId: Int) : ResponseEntity<Product> {
        val product = productService.getProductById(productId)

        return if (product != null)
            ResponseEntity.status(HttpStatus.OK).body(product)
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}