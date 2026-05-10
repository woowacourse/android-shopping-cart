package woowacourse.shopping.data.source.remote

import kotlinx.serialization.Serializable
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

@Serializable
data class ProductEntity(
    val id: String = "",
    val name: String,
    val price: Int,
    val imageUrl: String,
)

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        name = ProductName(name),
        price = Price(price),
        imageUrl = ImageUrl(imageUrl),
    )
