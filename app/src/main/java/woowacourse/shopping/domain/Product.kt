package woowacourse.shopping.domain

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    private val _price: Price,
    val imageUrl: String,
) : Serializable {
    val price get() = _price.value
}
