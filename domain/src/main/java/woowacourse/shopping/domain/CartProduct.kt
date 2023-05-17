package woowacourse.shopping.domain

import java.time.LocalDateTime

data class CartProduct(
    val time: LocalDateTime,
    val amount: Int = 0,
    val isChecked: Boolean,
    val product: Product
) {
    fun decreaseAmount(): CartProduct = copy(amount = amount - 1)

    fun increaseAmount(): CartProduct = copy(amount = amount + 1)

    fun changeChecked(isChecked: Boolean): CartProduct = copy(isChecked = isChecked)
}
