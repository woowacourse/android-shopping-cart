package woowacourse.shopping.view

import woowacourse.shopping.domain.model.Product

interface CountActionHandler {
    fun onIncreaseQuantityButtonClicked(product: Product)

    fun onDecreaseQuantityButtonClicked(product: Product)
}
