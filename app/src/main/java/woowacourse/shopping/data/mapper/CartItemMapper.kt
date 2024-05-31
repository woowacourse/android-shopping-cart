package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductWithQuantity

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

fun ProductWithQuantity.toNewCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productId = this.product.id,
        productName = this.product.name,
        price = this.product.price,
        imgUrl = this.product.imageUrl,
        quantity = 1,
    )
}

fun ProductWithQuantity.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productId = this.product.id,
        productName = this.product.name,
        price = this.product.price,
        imgUrl = this.product.imageUrl,
        quantity = this.quantity,
    )
}

fun ProductWithQuantity.toCartItem(): CartItem {
    return CartItem(
        id = this.product.id,
        productId = this.product.id,
        productName = this.product.name,
        price = this.product.price,
        imgUrl = this.product.imageUrl,
        quantity = this.quantity,
    )
}
