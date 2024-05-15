package woowacourse.shopping.view.detail

import woowacourse.shopping.domain.model.Product

interface OnClickDetail {
    fun clickClose()
    fun clickAddCart(product: Product)
}
