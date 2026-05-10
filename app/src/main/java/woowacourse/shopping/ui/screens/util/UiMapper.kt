package woowacourse.shopping.ui.screens.util

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.CartItems
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.screens.cart.CartUiModel
import woowacourse.shopping.ui.screens.product.ProductUiModel

fun Int.toPriceFormat(): String = "${"%,d".format(this)}원"

fun CartItem.toUiModel(product: Product): CartUiModel =
    CartUiModel(
        id = product.id,
        name = product.name,
        price = product.price.toPriceFormat(),
        imageUrl = product.imageUrl,
        cartAmount = amount.toString(),
    )

fun Product.toUiModel(cartItems: CartItems): ProductUiModel {
    val amount = cartItems.getCartItemAmount(this.id)
    return ProductUiModel(
        id = id,
        name = name,
        price = price.toPriceFormat(),
        imageUrl = imageUrl,
        cartAmount = amount.toString(),
        showAmountController = amount > 0,
    )
}
