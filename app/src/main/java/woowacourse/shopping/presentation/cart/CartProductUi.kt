package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.toUiModel

data class CartProductUi(
    val product: ProductUi,
    val count: Int = 0,
) {
    val isVisible: Boolean
        get() = count > 0
    val totalPrice: Int
        get() = product.price * count
}

fun CartProduct.toUiModel(): CartProductUi {
    return CartProductUi(product.toUiModel(), count)
}
