package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.Product

interface ShoppingCartEventHandler {
    fun onProductRemove(product: Product)

    fun onPaginationPrevious()

    fun onPaginationNext()
}
