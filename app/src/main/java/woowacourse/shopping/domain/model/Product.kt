package woowacourse.shopping.domain.model

import java.io.Serializable

data class Product(
    val productId: Long,
    val name: String,
    private val _price: Price,
    val imageUrl: String,
) : Serializable {
    val price get() = _price.value
}
