package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

fun CartItemEntity.toDomainModel(): CartItem {
    return CartItem(
        id = this.id,
        productId = this.productId,
        productName = this.productName,
        price = this.price,
        imgUrl = this.imgUrl,
        quantity = this.quantity,
    )
}

fun Product.toCartItemEntity(quantity: Int): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        productName = this.name,
        price = this.price,
        imgUrl = this.imageUrl,
        quantity = quantity,
    )
}
