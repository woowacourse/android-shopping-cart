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
        fun initScreen()
        fun updateProductCount(count: Int)
        fun navigateRecentProductDetail()
        fun handleAddCartSlide()
        fun setProductCountInfo(count: Int)
        fun exit()
    }
}
