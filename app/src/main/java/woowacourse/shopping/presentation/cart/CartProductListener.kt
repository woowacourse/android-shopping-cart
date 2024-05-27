package woowacourse.shopping.presentation.cart

interface CartProductListener : CartItemListener {
    fun deleteProduct(product: CartProductUi)
}
