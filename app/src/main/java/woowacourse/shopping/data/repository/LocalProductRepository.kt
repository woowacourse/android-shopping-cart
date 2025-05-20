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

    override fun fetchProduct(
        productId: Int,
        callback: (CartProduct?) -> Unit,
    ) {
        thread {
            callback(dao.getProduct(productId)?.toDomain())
        }
    }

    override fun fetchProducts(
        productIds: List<Int>,
        callback: (List<CartProduct>) -> Unit,
    ) {
        thread {
            callback(dao.getProducts(productIds).map { it.toDomain() })
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
}
