package woowacourse.shopping.view.home

import woowacourse.shopping.domain.model.Product

interface HomeClickListener {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()

    fun onShoppingCartButtonClick()

    fun onPlusButtonClick(product: Product)
}
