package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Product

interface HomeSetClickListener {

    fun setClickEventOnProduct(product: Product)
    fun setClickEventOnShowMoreButton()
    fun setClickEventOnOperatorButton(operator: Boolean, productInCart: Product)
}
