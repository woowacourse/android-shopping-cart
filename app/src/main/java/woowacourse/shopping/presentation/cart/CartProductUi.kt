package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.toUiModel

data class CartProductUi(
    val product: ProductUi,
    val count: Int,
)

fun CartProduct.toUiModel(): CartProductUi {
    return CartProductUi(product.toUiModel(), count)
}
