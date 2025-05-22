package woowacourse.shopping.product

import woowacourse.shopping.product.catalog.ProductUiModel

interface ProductQuantityHandler {
    fun onPlusQuantity(product: ProductUiModel)

    fun onMinusQuantity(product: ProductUiModel)
}
