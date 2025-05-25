package woowacourse.shopping.domain.model

import java.io.Serializable

data class Goods(
    val id: Int,
    val name: Name,
    val price: Price,
    val imageUrl: String,
    val quantity: Int,
) : Serializable {
    fun totalPrice(): Int = price.value * quantity

    fun updateQuantity(quantity: Int): Goods = copy(quantity = quantity)

    fun increaseQuantity(): Goods = copy(quantity = quantity + QUANTITY_STEP)

    fun decreaseQuantity(): Goods = copy(quantity = quantity - QUANTITY_STEP)

    companion object {
        private const val QUANTITY_STEP: Int = 1

        fun of(
            id: Int,
            name: String,
            price: Int,
            imageUrl: String,
            quantity: Int = 0,
        ): Goods =
            Goods(
                id,
                Name(name),
                Price(price),
                imageUrl,
                quantity,
            )
    }
}
