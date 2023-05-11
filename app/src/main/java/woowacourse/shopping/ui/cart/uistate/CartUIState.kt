package woowacourse.shopping.ui.cart.uistate

import woowacourse.shopping.domain.Product

data class CartUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
) {
    companion object {
        fun from(product: Product): CartUIState =
            CartUIState(product.imageUrl, product.name, product.price, product.id)
    }
}
