package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.local.CartProductEntity
import woowacourse.shopping.domain.model.Product

fun CartProductEntity.toDomain(): Product {
    return Product(
        id = this.productId,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
    )
}
