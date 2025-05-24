package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.CartItem

data class CartItemUiModel(
    val productId: Long,
    val productName: String,
    val imageUrl: String,
    val quantity: Int,
    val totalPrice: Int,
)

fun CartItem.toCartItemUiModel() =
    CartItemUiModel(
        product.id,
        product.name,
        product.imageUrl,
        quantity,
        totalPrice,
    )
