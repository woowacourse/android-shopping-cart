package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.toDomainCartItem
import woowacourse.shopping.data.toDomainGoods
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val shoppingDatabase: ShoppingDatabase,
) : CartRepository {
    override fun getAll(): List<CartItem> =
        shoppingDatabase.cartDao().getAll().map { entities ->
            entities.toDomainCartItem()
        }

    override fun fetchAllCartItems(onComplete: (List<CartItem>) -> Unit) {
        thread {
            val cartEntities = shoppingDatabase.cartDao().getAll()

            Handler(Looper.getMainLooper()).post {
                onComplete(cartEntities.map { it.toDomainCartItem() })
            }
        }
    }

    override fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        thread {
            shoppingDatabase.cartDao().insertAll(goods.toEntity())

            Handler(Looper.getMainLooper()).post {
                onComplete()
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

    override fun getPage(
        limit: Int,
        offset: Int,
    ): List<CartItem> =
        shoppingDatabase.cartDao().getPage(limit, offset).map { entities ->
            CartItem(entities.toDomainGoods(), entities.quantity)
        }

    override fun getAllItemsSize(): Int = shoppingDatabase.cartDao().getAllItemsSize()
}
