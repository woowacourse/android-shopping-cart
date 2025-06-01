package woowacourse.shopping.data.cart

import woowacourse.shopping.data.recentProducts.RecentProductEntity
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.model.product.Product

fun CartItemEntity.toCartItem(): CartItem = CartItem(Product(id, title, imageUrl, price), quantity = this.quantity)

fun CartItem.toCartItemEntity(): CartItemEntity =
    CartItemEntity(
        product.id,
        product.title,
        product.imageUrl,
        product.price,
        quantity = this.quantity,
    )

fun RecentProductEntity.toCartItem(): CartItem = CartItem(Product(id, title, imageUrl, price), quantity)

fun CartItem.toRecentProductEntity(): RecentProductEntity =
    RecentProductEntity(
        id = product.id,
        title = product.title,
        imageUrl = product.imageUrl,
        price = product.price,
        quantity = quantity,
    )
