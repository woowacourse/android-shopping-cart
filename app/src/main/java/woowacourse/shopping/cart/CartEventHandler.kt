package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

interface CartEventHandler {
    fun onDeleteProduct(cartProduct: ProductUiModel)

    fun onNextPage()

    fun onPrevPage()

    fun isNextButtonEnabled(): Boolean

    fun isPrevButtonEnabled(): Boolean

    fun getPage(): Int
}
