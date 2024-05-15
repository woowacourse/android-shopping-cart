package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem

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
