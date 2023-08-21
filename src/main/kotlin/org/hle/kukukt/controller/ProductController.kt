package org.hle.kukukt.controller

import jakarta.validation.Valid
import org.hle.kukukt.constant.ProductCategory
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {

    @GetMapping
    fun getProducts(
        @RequestParam category: ProductCategory?,
        @RequestParam search: String?
    ): ResponseEntity<List<Product>> {
        val productList = productService.getProducts(category, search)

        return ResponseEntity.status(HttpStatus.OK).body(productList)
    }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: Int): ResponseEntity<Product> {
        val product = productService.getProductById(productId)

        return if (product != null)
            ResponseEntity.status(HttpStatus.OK).body(product)
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PostMapping
    fun createProduct(@RequestBody @Valid productRequest: ProductRequest): ResponseEntity<Product> {
        val productId = productService.createProduct(productRequest)

        return if (productId != null) {
            val product = productService.getProductById(productId)
            ResponseEntity.status(HttpStatus.CREATED).body(product)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable productId: Int,
        @RequestBody @Valid productRequest: ProductRequest
    ): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        if (product == null) ResponseEntity.status(HttpStatus.NOT_FOUND).build<Product>()

        productService.updateProduct(productId, productRequest)

        val updatedProduct = productService.getProductById(productId)

        return if (updatedProduct != null)
            ResponseEntity.status(HttpStatus.OK).body(updatedProduct)
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}