package woowacourse.shopping.data.carts.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.util.toDomainCartItem
import woowacourse.shopping.data.util.toEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val shoppingDatabase: ShoppingDatabase,
) : CartRepository {
    override fun fetchAllCartItems(onComplete: (List<CartItem>) -> Unit) {
        thread {
            val cartEntities = shoppingDatabase.cartDao().getAll()

            Handler(Looper.getMainLooper()).post {
                onComplete(cartEntities.map { it.toDomainCartItem() })
            }
        }
    }

    override fun fetchPageCartItems(
        limit: Int,
        offset: Int,
        onComplete: (List<CartItem>) -> Unit,
    ) {
        thread {
            val cartEntities = shoppingDatabase.cartDao().getPage(limit, offset)

            Handler(Looper.getMainLooper()).post {
                onComplete(cartEntities.map { it.toDomainCartItem() })
            }
        }
    }

    override fun addOrIncreaseQuantity(
        goods: Goods,
        addQuantity: Int,
        onComplete: () -> Unit,
    ) {
        thread {
            shoppingDatabase
                .cartDao()
                .addOrIncreaseQuantity(goods.toEntity(addQuantity))

            Handler(Looper.getMainLooper()).post {
                onComplete()
            }
        }
    }

    override fun removeOrDecreaseQuantity(
        goods: Goods,
        removeQuantity: Int,
        onComplete: () -> Unit,
    ) {
        thread {
            shoppingDatabase
                .cartDao()
                .removeOrDecreaseQuantity(goods.toEntity(removeQuantity))

            Handler(Looper.getMainLooper()).post {
                onComplete()
            }
        }
    }

    override fun delete(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        thread {
            shoppingDatabase.cartDao().delete(goods.toEntity())

            Handler(Looper.getMainLooper()).post {
                onComplete()
            }
        }
    }

    override fun getAllItemsSize(onComplete: (Int) -> Unit) {
        thread {
            val size = shoppingDatabase.cartDao().getAllItemsSize()

            Handler(Looper.getMainLooper()).post {
                onComplete(size)
            }
        }
    }
}
