package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.Product

data class CartItemUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
) {
    companion object {
        fun from(product: Product): CartItemUIState =
            CartItemUIState(product.imageUrl, product.name, product.price, product.id)
    }
}
