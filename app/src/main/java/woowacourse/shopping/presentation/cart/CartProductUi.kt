package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

data class CartProductUi(
    val product: ShoppingUiModel.Product,
    val count: Int,
    var isVisible: Boolean = false
) {
    init {
        isVisible = product.count > 0 || isVisible
    }
}

fun CartProduct.toUiModel(isVisible: Boolean): CartProductUi {
    return CartProductUi(product.toShoppingUiModel(isVisible), product.count)
}

fun CartProductUi.increaseCount(): CartProductUi {
    return copy(count = (count + 1))
}

fun CartProductUi.decreaseCount(): CartProductUi {
    return copy(count = (count - 1).coerceAtLeast(1))
}
