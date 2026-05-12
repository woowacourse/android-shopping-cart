package woowacourse.shopping.data.network.product

import kotlinx.serialization.Serializable
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product

@Serializable
class ProductDto(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    fun toDomain(): Product {
        return Product(
            name = this.name,
            price = Money(this.price),
            imageUrl = this.imageUrl,
            id = this.id.toString(),
        )
    }
}
