package org.hle.kukukt.dao.impl

import org.hle.kukukt.dao.ProductDao
import org.hle.kukukt.dto.ProductQueryParams
import org.hle.kukukt.dto.ProductRequest
import org.hle.kukukt.model.Product
import org.hle.kukukt.rowmapper.ProductRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductDaoImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : ProductDao {
    override fun getProducts(queryParam: ProductQueryParams): List<Product> {
        val sb = StringBuilder()
        sb.append("SELECT product_id, product_name, category, image_url, price, stock, description, created_date,")
        sb.append(" last_modified_date FROM product WHERE 1 = 1")

        if (queryParam.category != null) sb.append(" AND category = :category")
        if (queryParam.search != null) sb.append(" AND product_name LIKE :search")
        sb.append(" ORDER BY ${queryParam.orderBy} ${queryParam.sort}")
        sb.append(" LIMIT :limit OFFSET :offset")

        val map = mapOf(
            "category" to queryParam.category?.name,
            "search" to "%${queryParam.search}%",
            "limit" to queryParam.limit,
            "offset" to queryParam.offset
        )

        return jdbcTemplate.query(sb.toString(), map, ProductRowMapper())
    }

    override fun countProduct(queryParam: ProductQueryParams): Int {
        val sb = StringBuilder()
        sb.append("SELECT count(*) FROM product WHERE 1 = 1")

        if (queryParam.category != null) sb.append(" AND category = :category")
        if (queryParam.search != null) sb.append(" AND product_name LIKE :search")

        val map = mapOf(
            "category" to queryParam.category?.name,
            "search" to "%${queryParam.search}%"
        )

        return jdbcTemplate.queryForObject(sb.toString(), map, Int::class.java)!!
    }

    override fun getProductById(productId: Int): Product? {
        val sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date," +
                " last_modified_date FROM product WHERE product_id = :productId"

        val map = mapOf("productId" to productId)

        val products = jdbcTemplate.query(sql, map, ProductRowMapper())
        return if (products.size > 0) products[0] else null
    }

    override fun createProduct(productRequest: ProductRequest): Int? {
        val sql =
            "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) " +
                    "VALUES(:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)"

        val now = Date()
        val map = mapOf(
            "productName" to productRequest.productName,
            "category" to productRequest.category.name,
            "imageUrl" to productRequest.imageUrl,
            "price" to productRequest.price,
            "stock" to productRequest.stock,
            "description" to productRequest.description,
            "createdDate" to now,
            "lastModifiedDate" to now
        )

        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(sql, MapSqlParameterSource(map), keyHolder)

        return keyHolder.key?.toInt()
    }

    override fun updateProduct(productId: Int, productRequest: ProductRequest) {
        val sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId"

        val map = mapOf(
            "productId" to productId,
            "productName" to productRequest.productName,
            "category" to productRequest.category.name,
            "imageUrl" to productRequest.imageUrl,
            "price" to productRequest.price,
            "stock" to productRequest.stock,
            "description" to productRequest.description,
            "lastModifiedDate" to Date()
        )

        jdbcTemplate.update(sql, map)
    }
}