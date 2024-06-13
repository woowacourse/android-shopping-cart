package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

data class CartProductUi(
    val product: ShoppingUiModel.Product,
    val count: Int,
)

fun CartProduct.toUiModel(isVisible: Boolean): CartProductUi {
    return CartProductUi(product.toShoppingUiModel(isVisible), product.count)
}
