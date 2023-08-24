package org.hle.kukukt.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.hle.kukukt.constant.ProductCategory
import org.hle.kukukt.dto.ProductRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val mapper = ObjectMapper()

    @Test
    fun getProduct_success() {
        mockMvc.get("/products/{productId}", 1) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.productName") { value("蘋果（澳洲）") }
            jsonPath("$.category") { value("FOOD") }
            jsonPath("$.imageUrl") { isNotEmpty() }
            jsonPath("$.price") { isNotEmpty() }
            jsonPath("$.stock") { isNotEmpty() }
            jsonPath("$.description") { isNotEmpty() }
            jsonPath("$.createdDate") { isNotEmpty() }
            jsonPath("$.lastModifiedDate") { isNotEmpty() }
        }
    }

    @Test
    fun getProduct_notFound() {
        mockMvc.get("/products/{productId}", 2000) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isNotFound() } }
    }

    @Transactional
    @Test
    fun createProduct_success() {
        val request = ProductRequest("Test food product", ProductCategory.FOOD, "http://test.com", 100, 2, null)

        mockMvc.post("/products") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            jsonPath("$.productName") { value("Test food product") }
            jsonPath("$.category") { value("FOOD") }
            jsonPath("$.imageUrl") { value("http://test.com") }
            jsonPath("$.price") { value(100) }
            jsonPath("$.stock") { value(2) }
            jsonPath("$.description") { isEmpty() }
            jsonPath("$.createdDate") { isNotEmpty() }
            jsonPath("$.lastModifiedDate") { isNotEmpty() }
        }
    }

    @Transactional
    @Test
    fun createProduct_illegalArgument() {
        val productRequest = object {
            val productName = "test food product"
        }

        mockMvc.post("/products") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(productRequest)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Transactional
    @Test
    fun updateProduct_success() {
        val request = ProductRequest("Test food product", ProductCategory.FOOD, "http://test.com", 100, 2, null)

        mockMvc.put("/products/{productId}", 3) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.productName") { value("Test food product") }
            jsonPath("$.category") { value("FOOD") }
            jsonPath("$.imageUrl") { value("http://test.com") }
            jsonPath("$.price") { value(100) }
            jsonPath("$.stock") { value(2) }
            jsonPath("$.description") { isEmpty() }
            jsonPath("$.createdDate") { isNotEmpty() }
            jsonPath("$.lastModifiedDate") { isNotEmpty() }
        }
    }

    @Transactional
    @Test
    fun updateProduct_illegalArgument() {
        val productRequest = object {
            val productName = "test food product"
        }

        mockMvc.put("/products/{productId}", 3) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(productRequest)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Transactional
    @Test
    fun updateProduct_notFound() {
        val request = ProductRequest("Test food product", ProductCategory.FOOD, "http://test.com", 100, 2, null)

        mockMvc.put("/products/{productId}", 2000) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Transactional
    @Test
    fun deleteProduct_success() {
        mockMvc.delete("/products/{productId}", 5) {

        }.andExpect {
            status { isNoContent() }
        }
    }

    @Transactional
    @Test
    fun deleteProduct_deleteNonExistedProduct() {
        mockMvc.delete("/products/{productId}", 2000) {

        }.andExpect {
            status { isNoContent() }
        }
    }

    @Test
    fun getProducts() {
        mockMvc.get("/products") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.limit") { isNumber() }
            jsonPath("$.offset") { isNumber() }
            jsonPath("$.total") { isNumber() }
            jsonPath("$.results") { isArray() }
        }
    }

    @Test
    fun getProducts_filtering() {
        mockMvc.get("/products") {
            accept = MediaType.APPLICATION_JSON
            params = CollectionUtils.toMultiValueMap(mapOf("search" to listOf("B"), "category" to listOf("CAR")))
        }.andExpect {
            status { isOk() }
            jsonPath("$.limit") { isNumber() }
            jsonPath("$.offset") { isNumber() }
            jsonPath("$.total") { isNumber() }
            jsonPath("$.results") { isArray() }
        }
    }

    @Test
    fun getProducts_sorting() {
        mockMvc.get("/products") {
            accept = MediaType.APPLICATION_JSON
            params = CollectionUtils.toMultiValueMap(mapOf("orderBy" to listOf("price"), "sort" to listOf("desc")))
        }.andExpect {
            status { isOk() }
            jsonPath("$.limit") { isNumber() }
            jsonPath("$.offset") { isNumber() }
            jsonPath("$.total") { isNumber() }
            jsonPath("$.results") { isArray() }
            jsonPath("$.results.[0].productId") { value(6) }
            jsonPath("$.results.[1].productId") { value(5) }
            jsonPath("$.results.[2].productId") { value(7) }
            jsonPath("$.results.[3].productId") { value(4) }
            jsonPath("$.results.[4].productId") { value(2) }
        }
    }

    @Test
    fun getProducts_pagination() {
        mockMvc.get("/products") {
            accept = MediaType.APPLICATION_JSON
            params = CollectionUtils.toMultiValueMap(mapOf("limit" to listOf("2"), "offset" to listOf("2")))
        }.andExpect {
            status { isOk() }
            jsonPath("$.limit") { isNumber() }
            jsonPath("$.offset") { isNumber() }
            jsonPath("$.total") { isNumber() }
            jsonPath("$.results") { isArray() }
            jsonPath("$.results.[0].productId") { value(5) }
            jsonPath("$.results.[1].productId") { value(4) }
        }
    }
}
