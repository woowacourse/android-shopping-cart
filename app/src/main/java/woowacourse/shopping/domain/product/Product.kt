package woowacourse.shopping.domain.product

import java.io.Serializable

data class Product(
    val imageUrl: String,
    val name: String,
    private val _price: Money,
): Serializable {
    val price: Int get() = _price.amount
}
