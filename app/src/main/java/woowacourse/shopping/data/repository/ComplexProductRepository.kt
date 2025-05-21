package woowacourse.shopping.data.repository

import woowacourse.shopping.data.api.ProductApi
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CatalogProducts
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ComplexProductRepository(
    private val dao: ProductDao,
    private val api: ProductApi,
) : ProductRepository {
    override fun fetchProducts(
        lastId: Int,
        count: Int,
        callback: (CatalogProducts) -> Unit,
    ) {
        thread {
            val cartProducts =
                api
                    .getProducts(lastId, count)
                    .execute()
                    .body()
                    ?.map { it.toDomain() } ?: emptyList()

            fetchHasMoreProducts(cartProducts.lastOrNull()?.product?.id ?: lastId) { hasMore ->
                callback(CatalogProducts(cartProducts, hasMore))
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
            callback(dao.getMaxId() > lastId)
        }
    }
}
