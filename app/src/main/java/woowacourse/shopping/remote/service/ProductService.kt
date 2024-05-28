package woowacourse.shopping.remote.service

import woowacourse.shopping.remote.model.ProductPageResponse
import woowacourse.shopping.remote.model.ProductResponse

interface ProductService {
    fun fetchProducts(
        currentPage: Int,
        size: Int,
    ): ProductPageResponse

    fun fetchProductById(id: Long): ProductResponse

    fun canLoadMore(
        page: Int,
        size: Int,
    ): Boolean
}
