package woowacourse.shopping.view.common

import woowacourse.shopping.domain.model.Product

interface QuantityControlEventHandler {
    fun onQuantityIncreaseClick(item: Product)

    fun onQuantityDecreaseClick(item: Product)
}
