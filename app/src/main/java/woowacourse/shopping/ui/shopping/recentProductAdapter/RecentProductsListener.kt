package woowacourse.shopping.ui.shopping.recentProductAdapter

import woowacourse.shopping.model.ProductUIModel

fun interface RecentProductsListener {
    fun onClickItem(data: ProductUIModel)
}
