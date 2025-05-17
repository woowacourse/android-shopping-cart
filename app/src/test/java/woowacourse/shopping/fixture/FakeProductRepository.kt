package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

val fakeProductRepository =
    object : ProductRepository {
        override fun findProductById(
            id: Long,
            callback: (Product?) -> Unit,
        ) {
            val result = dummyProductsFixture.find { it.id == id }
            callback(result)
        }

        override fun findProductsByIds(
            ids: List<Long>,
            callback: (List<Product>) -> Unit,
        ) {
            val results = dummyProductsFixture.filter { ids.contains(it.id) }
            callback(results)
        }

        override fun loadProducts(
            lastItemId: Long,
            loadSize: Int,
            callback: (List<Product>, Boolean) -> Unit,
        ) {
            val products = dummyProductsFixture.filter { it.id > lastItemId }.take(loadSize)
            val lastId = products.lastOrNull()?.id ?: return callback(products, false)
            val hasMore = dummyProductsFixture.any { it.id > lastId }
            callback(products, hasMore)
        }
    }
