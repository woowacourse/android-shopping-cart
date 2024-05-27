package woowacourse.shopping

import woowacourse.shopping.data.cartItem.CartItemDatabase
import woowacourse.shopping.data.cartItem.CartItemEntity
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemDatabase
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemEntity
import woowacourse.shopping.domain.model.Product
import java.time.LocalDateTime

object TestFixture {
    fun CartItemDatabase.deleteAll() {
        this.openHelper.writableDatabase.execSQL("DELETE FROM ${CartItemDatabase.CART_ITEMS_DB_NAME}")
    }

    fun RecentViewedItemDatabase.deleteAll() {
        this.openHelper.writableDatabase.execSQL("DELETE FROM ${RecentViewedItemDatabase.RECENT_VIEWED_ITEM_DB_NAME}")
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

    fun makeRecentViewedEntity(
        product: Product,
        localDateTime: LocalDateTime = LocalDateTime.now(),
    ): RecentViewedItemEntity {
        return RecentViewedItemEntity(productId = product.id, product = product, viewedAt = localDateTime)
    }
}
