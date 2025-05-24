package woowacourse.shopping.view.product.detail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.common.QuantityControlEventHandler

interface ProductDetailEventHandler : QuantityControlEventHandler<Product> {
    fun onProductAddClick()

    fun onLastProductClick()
}
