package woowacourse.shopping.view.products

import woowacourse.shopping.domain.model.Product

interface ProductListActionHandler {
    fun onProductItemClicked(product: Product)

    fun onShoppingCartButtonClicked()

    fun onMoreButtonClicked()
}
