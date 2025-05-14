package woowacourse.shopping.data.repository

import woowacourse.shopping.data.repository.dummyProducts
import woowacourse.shopping.domain.model.Product

object ProductDummyRepositoryImpl {
    fun fetchProducts(
        count: Int,
        lastId: Int = 0,
    ): List<Product> = dummyProducts.filter { it.id > lastId }.take(count)

    fun fetchProductDetail(id: Int): Product? = dummyProducts.find { it.id == id }
}
