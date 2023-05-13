package woowacourse.shopping.view.shoppingmain

import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ShoppingMainContract {
    interface View {
        var presenter: Presenter

        fun showProductDetailPage(): (ProductUIModel) -> Unit
    }

    interface Presenter {
        fun getMainProducts(): List<ProductUIModel>
        fun getRecentProducts(): List<RecentProductUIModel>
        fun setProductOnClick()
    }
}
