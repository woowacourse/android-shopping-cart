package woowacourse.shopping.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import woowacourse.shopping.domain.Product
import java.util.UUID

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String
)

fun ProductDto.toDomain(): Product = Product(
    productId = UUID.nameUUIDFromBytes(id.toString().toByteArray()),
    name = name,
    price = price,
    imageUri = imageUrl
)
