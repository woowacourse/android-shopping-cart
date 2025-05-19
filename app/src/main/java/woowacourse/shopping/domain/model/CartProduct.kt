package woowacourse.shopping.domain.model

import woowacourse.shopping.domain.model.Product.Companion.EMPTY_PRODUCT

data class CartProduct(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice: Int get() = product.price * quantity

    companion object {
        val EMPTY_CART_PRODUCT = CartProduct(EMPTY_PRODUCT, 0)
    }
}
