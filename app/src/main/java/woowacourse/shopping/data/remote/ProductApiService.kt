package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Product

interface ProductApiService {
    fun loadById(productId: Long): Product

    fun load(
        startPage: Int,
        pageSize: Int,
    ): List<Product>
}
