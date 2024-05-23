package woowacourse.shopping.presentation.cart

interface CartItemListener {
    fun increaseProductCount(id: Long)

    fun decreaseProductCount(id: Long)
}
