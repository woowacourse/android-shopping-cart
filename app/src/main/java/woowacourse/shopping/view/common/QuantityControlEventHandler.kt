package woowacourse.shopping.view.common

import woowacourse.shopping.domain.Product

interface QuantityControlEventHandler {
    fun onQuantityIncreaseClick(item: Product)

    fun onQuantityDecreaseClick(item: Product)
}
