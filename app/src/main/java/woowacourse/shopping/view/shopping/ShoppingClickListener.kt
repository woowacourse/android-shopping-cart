package woowacourse.shopping.view.shopping

import woowacourse.shopping.domain.model.Product

interface ShoppingClickListener {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()

    fun onShoppingCartButtonClick()

    fun onPlusButtonClick(product: Product)
}
