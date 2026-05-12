package woowacourse.shopping.domain.model.product

import woowacourse.shopping.domain.model.Price
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String,
    val productTitle: ProductTitle,
    val price: Price,
)
