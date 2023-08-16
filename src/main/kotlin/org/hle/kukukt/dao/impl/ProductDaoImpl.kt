package org.hle.kukukt.dao.impl

import org.hle.kukukt.dao.ProductDao
import org.hle.kukukt.model.Product
import org.hle.kukukt.rowmapper.ProductRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ProductDaoImpl(val jdbcTemplate: NamedParameterJdbcTemplate) : ProductDao {

    override fun getProductById(productId: Int): Product? {
        val sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date," +
                " last_modified_date FROM product WHERE product_id = :productId"

        val map = mapOf("productId" to productId)

        val products = jdbcTemplate.query(sql, map, ProductRowMapper())
        return if (products.size > 0) products[0] else null
    }
}