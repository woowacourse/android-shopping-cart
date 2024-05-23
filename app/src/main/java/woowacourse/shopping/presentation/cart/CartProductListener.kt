package woowacourse.shopping.presentation.cart

interface CartProductListener : CartItemListener {
    fun delete(product: CartProductUi)
}
