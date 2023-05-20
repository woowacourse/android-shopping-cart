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
        fun showSelectCartProductCountScreen(product: ProductUiModel)
    }

    interface Presenter {
        val product: ProductUiModel
        val recentProduct: RecentProductUiModel?
        val isRecentProduct: Boolean
        fun initScreen()
        fun updateProductCount(count: Int)
        fun navigateRecentProductDetail()
        fun handleAddCartClick()
        fun setProductCountInfo(count: Int)
        fun exit()
    }
}
