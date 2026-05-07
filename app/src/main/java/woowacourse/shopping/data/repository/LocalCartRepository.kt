package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.local.CartDao
import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RemoveItemResult
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class LocalCartRepository(
    private val cartDao: CartDao,
    private val productRepository: ProductRepository,
) : CartRepository {
    override suspend fun getCart(): Cart {
        val rows = cartDao.observeAll()
        val items =
            rows.mapNotNull { row ->
                runCatching { productRepository.getProductById(row.productId) }
                    .getOrNull()
                    ?.let { CartItem(it, row.quantity) }
            }
        return Cart(items)
    }

    override suspend fun getTotalCartSize(): Int = cartDao.getTotalCartSize()

    override suspend fun addItem(product: Product): AddItemResult {
        cartDao.addOrIncrement(product.id)
        return AddItemResult.NewAdded(getCart())
    }

    override suspend fun deleteItem(id: String): RemoveItemResult {
        val isDeleted = cartDao.deleteItem(id)
        return if (isDeleted > 0) {
            RemoveItemResult.Success(getCart())
        } else {
            RemoveItemResult.NotFoundItem
        }
    }
}
