package woowacourse.shopping.data.service

import okhttp3.Response

interface ProductService {
    fun getProductById(id: Long): Response

    fun getProductsByIds(ids: List<Long>): Response

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): Response
}
