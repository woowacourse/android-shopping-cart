package woowacourse.shopping

import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.Product

object TestFixture {
    fun CartItemDatabase.deleteAll() {
        this.openHelper.writableDatabase.execSQL("DELETE FROM ${CartItemDatabase.CART_ITEMS_DB_NAME}")
    }

    fun makeCartItemEntity(): CartItemEntity {
        return CartItemEntity(
            product =
                Product(
                    imageUrl = "",
                    price = 10000,
                    name = "아메리카노",
                    id = 0L,
                ),
        )
    }
}
