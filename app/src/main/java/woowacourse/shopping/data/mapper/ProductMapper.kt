package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.local.CartProductEntity
import woowacourse.shopping.data.model.remote.ProductEntity
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

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
    )
}
