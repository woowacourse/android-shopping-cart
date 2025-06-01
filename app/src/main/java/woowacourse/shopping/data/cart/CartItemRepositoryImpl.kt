package woowacourse.shopping.data.cart

import woowacourse.shopping.mapper.toCartItem
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class CartItemRepositoryImpl(
    private val dao: CartItemDao,
) : CartItemRepository {
    override fun getAllCartItemSize(callback: (Int) -> Unit) {
        thread {
            callback(dao.getAll().size)
        }
    }

    override fun getAllCartItem(callback: (List<ProductUiModel>) -> Unit) {
        thread {
            val items = dao.getAll().map { it.toUiModel() }
            callback(items)
        }
    }

    override fun subListCartItems(
        startIndex: Int,
        endIndex: Int,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        thread {
            val items = dao.getAll().map { it.toUiModel() }
            callback(items.subList(startIndex, endIndex))
        }
    }

    override fun insertCartItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        thread {
            dao.insertCartItem(product.toCartItem())
            onComplete()
        }
    }

    override fun updateCartItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        thread {
            dao.updateQuantity(product.id, product.quantity)
            onComplete()
        }
    }

    override fun deleteCartItemById(
        productId: Long,
        onComplete: () -> Unit,
    ) {
        thread {
            dao.deleteByProductId(productId)
            onComplete()
        }
    }

    override fun findCartItem(
        product: ProductUiModel,
        callback: (CartItem?) -> Unit,
    ) {
        thread {
            val result = dao.getCartItemById(product.id)
            callback(result)
        }
    }

    override fun updateOrInsertItem(
        product: ProductUiModel,
        callback: () -> Unit,
    ) {
        findCartItem(product) { existing ->
            if (existing != null) {
                updateCartItem(product, callback)
            } else {
                insertCartItem(product, callback)
            }
        }
    }
}
