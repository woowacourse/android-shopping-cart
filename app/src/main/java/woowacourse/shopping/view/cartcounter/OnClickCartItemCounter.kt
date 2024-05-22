package woowacourse.shopping.view.cartcounter

import woowacourse.shopping.domain.model.Product

interface OnClickCartItemCounter {
    fun clickIncrease(product: Product)

    fun clickDecrease(product: Product)
}
