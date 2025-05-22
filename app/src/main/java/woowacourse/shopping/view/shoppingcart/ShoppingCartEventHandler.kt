package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.Product

interface ShoppingCartEventHandler {
    fun onRemoveProduct(product: Product)

    fun onGoToPreviousPage()

    fun onGoToNextPage()
}
