package woowacourse.shopping.feature.cart.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.domain.model.cart.CartItems

@Stable
data class CartInfo(
    val id: String,
    val productImageUrl: String,
    val productName: String,
    val price: String,
) {
    companion object {
        val PREVIEW =
            CartInfo(
                id = "",
                productImageUrl = "",
                productName = "리자몽",
                price = "10,000원",
            )
    }
}

fun CartItems.toUiModel(): ImmutableList<CartInfo> =
    items
        .map { cartItem ->
            CartInfo(
                id = cartItem.product.id,
                productImageUrl = cartItem.product.imageUrl,
                productName = cartItem.product.productTitle.value,
                price = "%,d원".format(cartItem.product.price.value),
            )
        }.toImmutableList()
