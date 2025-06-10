package woowacourse.shopping.data.shoppingCart.storage

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao

object LocalShoppingCartDataSource : ShoppingCartDataSource {
    private lateinit var dao: ShoppingCartDao

    fun init(applicationContext: Context) {
        val db =
            Room
                .databaseBuilder(
                    applicationContext,
                    ShoppingCartDatabase::class.java,
                    "shoppingCart",
                ).build()

        dao = db.shoppingCartDao()
    }

    override fun load(): List<CartItemEntity> = dao.load()

    override fun upsert(cartItem: CartItemEntity) = dao.upsert(cartItem)

    override fun remove(cartItem: CartItemEntity) = dao.remove(cartItem)

    override fun update(cartItems: List<CartItemEntity>) = dao.replaceAll(cartItems)

    override fun quantityOf(productId: Long): Int = dao.quantityOf(productId)
}
