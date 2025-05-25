package woowacourse.shopping.data.cart

import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.model.product.Product

fun CartItemEntity.toCartItem(): CartItem = CartItem(Product(id, title, imageUrl, price), quantity = this.quantity)

fun CartItem.toEntity(): CartItemEntity =
    CartItemEntity(
        product.id,
        product.title,
        product.imageUrl,
        product.price,
        quantity = this.quantity,
    )
