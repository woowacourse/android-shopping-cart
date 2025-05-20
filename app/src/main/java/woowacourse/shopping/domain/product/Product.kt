package woowacourse.shopping.domain.product

import woowacourse.shopping.domain.Quantity

data class Product(
    val id: Long,
    val name: String,
    val imgUrl: String,
    private val price: Price,
    private val quantity: Quantity,
) {
    val priceValue: Int = price.value
    val quantityValue: Int = price.value
}
