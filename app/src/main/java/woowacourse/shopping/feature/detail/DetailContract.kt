package woowacourse.shopping.feature.detail

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface DetailContract {
    interface View {
        fun showCartScreen()
        fun hideRecentScreen()
        fun setRecentScreen(title: String, money: String)
        fun showRecentProductDetailScreen(recentProductUiModel: RecentProductUiModel)
        fun exitDetailScreen()
        fun navigateMainScreen()
    }

    interface Presenter {
        val product: ProductUiModel
        val recentProduct: RecentProductUiModel?
        fun initScreen()
        fun addCart()
        fun navigateRecentProductDetail()
        fun exit()
    }
}