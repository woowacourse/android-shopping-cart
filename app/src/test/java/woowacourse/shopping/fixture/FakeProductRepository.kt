package woowacourse.shopping.fixture

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

val fakeProductRepository =
    object : ProductRepository {
        override fun findProductById(id: Long): Result<Product> =
            runCatching {
                val foundProduct = dummyProductsFixture.find { it.id == id }
                if (foundProduct == null) throw NoSuchElementException()
                foundProduct
            }

        override fun findProductsByIds(ids: List<Long>): Result<List<Product>> {
            val results = dummyProductsFixture.filter { ids.contains(it.id) }
            return Result.success(results)
        }

        override fun loadProducts(
            offset: Int,
            loadSize: Int,
        ): Result<PageableItem<Product>> =
            runCatching {
                val totalSize = dummyProductsFixture.size

                if (offset >= totalSize) {
                    return@runCatching PageableItem(emptyList(), false)
                }

                val sublist = dummyProductsFixture.drop(offset).take(loadSize)
                val hasMore = offset + loadSize < totalSize

                PageableItem(sublist, hasMore)
            }
    }
