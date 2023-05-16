package woowacourse.shopping.presentation.ui.home.adapter

import woowacourse.shopping.domain.model.Product

interface ClickListenerByViewType {

    fun setClickEventOnProduct(product: Product)
    fun setClickEventOnShowMore()
}
