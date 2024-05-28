package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.local.CartProductDto
import woowacourse.shopping.data.model.local.ProductHistoryDto
import woowacourse.shopping.data.model.remote.ProductDto
import woowacourse.shopping.domain.model.Product

fun CartProductDto.toDomain(): Product {
    return Product(
        id = this.productId,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
    )
}

fun ProductDto.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
    )
}

fun ProductHistoryDto.toDomain(): Product {
    return Product(
        id = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}
