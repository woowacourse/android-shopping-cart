package woowacourse.shopping.local.mapper

import woowacourse.shopping.data.model.local.CartProductDto
import woowacourse.shopping.local.model.CartProductEntity

fun CartProductEntity.toData(): CartProductDto {
    return CartProductDto(
        productId = this.productId,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
        createAt = this.createAt,
    )
}
