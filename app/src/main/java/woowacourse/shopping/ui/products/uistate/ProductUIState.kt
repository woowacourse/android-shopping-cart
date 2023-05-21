package woowacourse.shopping.ui.products.uistate

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

data class ProductUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
    val cartItemId: Long? = null,
    val count: Int? = null,
) {
    companion object {
        fun from(product: Product): ProductUIState =
            ProductUIState(product.imageUrl, product.name, product.price, product.id)

        fun from(cartItem: CartItem): ProductUIState =
            ProductUIState(
                cartItem.product.imageUrl,
                cartItem.product.name,
                cartItem.product.price,
                cartItem.product.id,
                cartItem.id,
                cartItem.count
            )
    }
}
