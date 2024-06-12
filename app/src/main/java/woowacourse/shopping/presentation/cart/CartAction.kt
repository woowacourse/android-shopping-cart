package woowacourse.shopping.presentation.cart

interface CartAction {
    fun deleteProduct(cart: CartProductUi)
    fun increaseCount(cart: CartProductUi)
    fun decreaseCount(cart: CartProductUi)
}
