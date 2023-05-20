package woowacourse.shopping.presentation.ui.common

import woowacourse.shopping.domain.model.Product

interface QuantityControlClickListener {

    fun setClickEventOnOperatorButton(operator: Boolean, productInCart: Product)
}
