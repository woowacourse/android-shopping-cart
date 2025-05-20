package woowacourse.shopping.view.main.vm

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.product.Product

data class ProductState(
    val item: Product,
    val quantity: Quantity,
) {
    fun increase() = copy(quantity = quantity + 1)

    fun decrease() = copy(quantity = quantity - 1)

    val quantityVisible: Boolean
        get() = quantity.hasQuantity()
}
