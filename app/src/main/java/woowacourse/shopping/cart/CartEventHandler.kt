package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

interface CartEventHandler {
    fun onDeleteProduct(product: ProductUiModel)

    fun onNextPage()

    fun onPrevPage()
}
