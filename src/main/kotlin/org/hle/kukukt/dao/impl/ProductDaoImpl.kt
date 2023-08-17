package org.hle.kukukt.dao.impl

import org.hle.kukukt.dao.ProductDao
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.rowmapper.ProductRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
class ProductDaoImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : ProductDao {

    override fun getProductById(productId: Int): Product? {
        val sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date," +
                " last_modified_date FROM product WHERE product_id = :productId"

        val map = mapOf("productId" to productId)

        val products = jdbcTemplate.query(sql, map, ProductRowMapper())
        return if (products.size > 0) products[0] else null
    }

    override fun createProduct(productRequest: ProductRequest): Int? {
        val sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) " +
                "VALUES(:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)"

        val now = Date()
        val map = mapOf("productName" to productRequest.productName,
            "category" to productRequest.category.name,
            "imageUrl" to productRequest.imageUrl,
            "price" to productRequest.price,
            "stock" to productRequest.stock,
            "description" to productRequest.description,
            "createdDate" to now,
            "lastModifiedDate" to now)

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(sql, MapSqlParameterSource(map), keyHolder)

        return keyHolder.key?.toInt()
    }
}