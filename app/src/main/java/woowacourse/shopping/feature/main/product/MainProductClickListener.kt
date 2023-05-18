package woowacourse.shopping.feature.main.product

import woowacourse.shopping.model.ProductUiModel

interface MainProductClickListener {
    fun onPlusClick(product: ProductUiModel, previousCount: Int)
    fun onMinusClick(product: ProductUiModel, previousCount: Int)
    fun onProductClick(product: ProductUiModel)
}
