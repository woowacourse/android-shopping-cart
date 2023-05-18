package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.CartProduct

data class CartUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
    val count: Int,
) {
    companion object {
        fun from(product: CartProduct): CartUIState =
            CartUIState(product.imageUrl, product.name, product.price, product.id, product.count)
    }
}
