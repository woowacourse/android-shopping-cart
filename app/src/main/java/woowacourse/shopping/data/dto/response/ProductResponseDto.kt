package woowacourse.shopping.data.dto.response

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

data class ProductResponseDto(
    val id: String,
    val imageUrl: String,
    val name: String,
    val price: Int
)

fun ProductResponseDto.toDomainModel(): Product {
    return Product(
        id = this.id,
        imageUrl = this.imageUrl,
        productTitle = ProductTitle(this.name),
        price = Price(this.price)
    )
}
