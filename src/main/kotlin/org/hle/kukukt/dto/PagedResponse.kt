package org.hle.kukukt.dto

class PagedResponse<T>(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val results: List<T>
) {
}