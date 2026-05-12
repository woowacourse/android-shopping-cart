package woowacourse.shopping.feature.cart.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.domain.model.cart.CartItem

@Stable
data class CartInfo(
    val id: String,
    val productImageUrl: String,
    val productName: String,
    val formattedPrice: String,
    val formattedQuantity: String,
) {
    companion object {
        val PREVIEW =
            CartInfo(
                id = "",
                productImageUrl = "",
                productName = "리자몽",
                formattedPrice = "10,000원",
                formattedQuantity = "1",
            )
    }
}

fun List<CartItem>.toUiModel(): ImmutableList<CartInfo> =
    this
        .map { cartItem ->
            CartInfo(
                id = cartItem.product.id,
                productImageUrl = cartItem.product.imageUrl,
                productName = cartItem.product.productTitle.value,
                formattedPrice = "%,d원".format(cartItem.product.price.value),
                formattedQuantity = cartItem.quantity.value.toString(),
            )
        }.toImmutableList()
