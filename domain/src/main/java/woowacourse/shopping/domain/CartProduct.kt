package woowacourse.shopping.domain

import java.time.LocalDateTime

data class CartProduct(
    val time: LocalDateTime,
    val amount: Int = 1,
    val isChecked: Boolean,
    val product: Product
) {
    fun decreaseAmount(): CartProduct {
        if (amount > 1) return copy(amount = amount - 1)
        return this
    }

    fun increaseAmount(): CartProduct {
        return copy(amount = amount + 1)
    }

    fun changeChecked(isChecked: Boolean): CartProduct {
        return copy(isChecked = isChecked)
    }
}
