package woowacourse.shopping.data.network.entitiy

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product

data class ProductEntity(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Int,
    val quantity: Int,
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            imgUrl = imgUrl,
            price = Price(price),
            quantity = Quantity(quantity),
        )
    }
}
