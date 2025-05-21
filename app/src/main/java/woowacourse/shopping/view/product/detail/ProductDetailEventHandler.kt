package woowacourse.shopping.view.product.detail

import woowacourse.shopping.view.common.QuantityControlEventHandler

interface ProductDetailEventHandler : QuantityControlEventHandler {
    fun onProductAddClick()
}
