package woowacourse.shopping.remote.mapper

import woowacourse.shopping.data.model.remote.ProductDto
import woowacourse.shopping.remote.model.ProductResponse

fun ProductResponse.toData(): ProductDto {
    return ProductDto(
        id = this.id,
        name = this.name,
        price = this.price,
        quantity = this.quantity,
        imageUrl = this.imageUrl,
    )
}
