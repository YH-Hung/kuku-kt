package org.hle.kukukt.rowmapper

import org.hle.kukukt.constant.ProductCategory
import org.hle.kukukt.model.Product
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class ProductRowMapper : RowMapper<Product> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Product = Product(
        rs.getInt("product_id"),
        rs.getString("product_name"),
        ProductCategory.valueOf(rs.getString("category")),
        rs.getString("image_url"),
        rs.getInt("price"),
        rs.getInt("stock"),
        rs.getString("description"),
        rs.getTimestamp("created_date"),
        rs.getTimestamp("last_modified_date")
    )
}