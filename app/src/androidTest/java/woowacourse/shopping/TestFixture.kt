package woowacourse.shopping

import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.Product

object TestFixture {
    fun CartItemDatabase.deleteAll() {
        this.openHelper.writableDatabase.execSQL("DELETE FROM ${CartItemDatabase.CART_ITEMS_DB_NAME}")
    }

    fun makeCartItemEntity(
        productId: Long,
        name: String,
        quantity: Int,
        price: Int = 10000,
    ): CartItemEntity {
        return CartItemEntity(
            productId = productId,
            product =
                Product(
                    imageUrl = "",
                    price = price,
                    name = name,
                    id = productId,
                ),
            quantity = quantity,
        )
    }
}
