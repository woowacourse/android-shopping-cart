package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.model.Product

interface ProductApiService {
    fun loadById(productId: Long): Product

    fun load(
        startPage: Int,
        pageSize: Int,
    ): List<Product>
}
