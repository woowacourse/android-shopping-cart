package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProducts
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class LocalProductRepository(
    private val dao: ProductDao,
) : ProductRepository {
    override fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit,
    ) {
        thread {
            val cartProducts = dao.getNextProducts(lastId, count).map { it.toDomain() }

            fetchHasMoreProducts(cartProducts.lastOrNull()?.product?.id ?: lastId) { hasMore ->
                callback(
                    CatalogProducts(products = cartProducts, hasMore = hasMore),
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
        callback: (CartProduct) -> Unit,
    ) {
        thread {
            callback(
                dao.getProduct(id)?.toDomain() ?: throw NoSuchElementException(),
            )
        }
    }
}
