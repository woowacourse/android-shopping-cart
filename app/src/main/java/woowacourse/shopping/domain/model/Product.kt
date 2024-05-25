package woowacourse.shopping.domain.model

import woowacourse.shopping.data.db.cart.CartItemEntity
import woowacourse.shopping.data.db.recent.RecentProductEntity
import java.time.LocalDateTime

data class Product(
    val id: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
)

fun Product.toCartItemEntity(quantity: Int): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        productName = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        quantity = quantity,
    )
}

fun Product.toRecentProductEntity(): RecentProductEntity {
    val localDateTime = LocalDateTime.now().toString()
    return RecentProductEntity(
        productId = this.id,
        productName = this.name,
        imageUrl = this.imageUrl,
        dateTime = localDateTime,
    )
}
