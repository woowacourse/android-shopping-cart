package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId

object CartItemUiModelMapper {
    fun toUiModels(
        cartItems: List<CartItem>,
        productsById: Map<ProductId, Product>,
    ): List<CartItemUiModel> =
        cartItems.mapNotNull { cartItem ->
            val product = productsById[cartItem.productId] ?: return@mapNotNull null

            CartItemUiModel(
                productId = cartItem.productId,
                name = product.name,
                imageUrl = product.imageUrl,
                price = product.price.value,
                quantity = cartItem.quantity,
            )
        }
}
