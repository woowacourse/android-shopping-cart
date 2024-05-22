package woowacourse.shopping.presentation.shopping

import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel

fun Product.toShoppingUiModel(): ShoppingUiModel.Product {
    return ShoppingUiModel.Product(id, name, price, imageUrl)
}

fun Product.toUiModel(): ProductUi {
    return ProductUi(id, name, price, imageUrl)
}
