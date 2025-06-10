package woowacourse.shopping.data.shoppingCart.storage

import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao

object LocalShoppingCartDataSource : ShoppingCartDataSource {
    private lateinit var dao: ShoppingCartDao

    fun init(shoppingCartDao: ShoppingCartDao) {
        dao = shoppingCartDao
    }

    override fun load(): List<CartItemEntity> = dao.load()

    override fun upsert(cartItem: CartItemEntity) = dao.upsert(cartItem)

    override fun remove(cartItem: CartItemEntity) = dao.remove(cartItem)

    override fun update(cartItems: List<CartItemEntity>) = dao.replaceAll(cartItems)

    override fun quantityOf(productId: Long): Int = dao.quantityOf(productId)
}
