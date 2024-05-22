package woowacourse.shopping.domain.model

import woowacourse.shopping.data.db.cart.CartItemEntity

data class Product(
    val id: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
)

fun Product.toEntity(quantity: Int): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        productName = this.name,
        price = this.price,
        imgUrl = this.imageUrl,
        quantity = quantity,
    )
}
