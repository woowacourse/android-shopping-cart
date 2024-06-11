package woowacourse.shopping.presentation.cart

interface CartAction {
    fun deleteProduct(product: CartProductUi)
    fun increaseCount(product: CartProductUi)
    fun decreaseCount(product: CartProductUi)
}
