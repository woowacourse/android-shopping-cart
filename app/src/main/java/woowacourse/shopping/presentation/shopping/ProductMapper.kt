package woowacourse.shopping.presentation.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

fun Product.toShoppingUiModel(isVisible: Boolean): ShoppingUiModel.Product {
    return ShoppingUiModel.Product(id, name, price, imageUrl, count, isVisible)
}
