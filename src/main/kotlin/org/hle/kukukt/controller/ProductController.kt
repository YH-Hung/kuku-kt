package org.hle.kukukt.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hle.kukukt.constant.ProductCategory
import org.hle.kukukt.dto.PagedResponse
import org.hle.kukukt.dto.ProductQueryParams
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/products")
class ProductController(val productService: ProductService) {

    @GetMapping
    fun getProducts(
        @RequestParam category: ProductCategory?,
        @RequestParam search: String?,
        @RequestParam(required = false, defaultValue = "created_date") orderBy: String,
        @RequestParam(required = false, defaultValue = "desc") sort: String,
        @RequestParam(required = false, defaultValue = "5") @Max(1000) @Min(0) limit: Int,
        @RequestParam(required = false, defaultValue = "0") @Min(0) offset: Int
    ): ResponseEntity<PagedResponse<Product>> {
        val queryParam = ProductQueryParams(category, search, orderBy, sort, limit, offset)
        val productList = productService.getProducts(queryParam)
        val total = productService.countProduct(queryParam)

        val page = PagedResponse(limit, offset, total, productList)

        return ResponseEntity.status(HttpStatus.OK).body(page)
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

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Int): ResponseEntity<Any> {
        productService.deleteProductById(productId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}