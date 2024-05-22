package woowacourse.shopping.view.detail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentlyProduct

interface OnClickDetail {
    fun clickClose()

    fun clickAddCart(product: Product)

    fun clickRecently(recentlyProduct: RecentlyProduct)
}
