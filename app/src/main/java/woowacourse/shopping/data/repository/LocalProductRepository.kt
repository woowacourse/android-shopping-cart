package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class LocalProductRepository(
    private val dao: ProductDao,
) : ProductRepository {
    override fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (Products) -> Unit,
    ) {
        thread {
            val products = dao.getNextProducts(lastId, count).map { it.toDomain() }

            fetchHasMoreProducts(products.lastOrNull()?.id ?: lastId) { hasMore ->
                callback(
                    Products(products = products, hasMore = hasMore),
                )
            }
        }
    }

    private fun fetchHasMoreProducts(
        lastId: Int,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            callback(
                dao.getMaxId() > lastId,
            )
        }
    }

    override fun fetchProductDetail(
        id: Int,
        callback: (Product) -> Unit,
    ) {
        thread {
            callback(
                dao.getProduct(id)?.toDomain() ?: throw NoSuchElementException(),
            )
        }
    }
}
