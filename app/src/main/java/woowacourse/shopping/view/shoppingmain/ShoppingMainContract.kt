package woowacourse.shopping.view.shoppingmain

import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

interface ShoppingMainContract {
    interface View {
        var presenter: Presenter
        fun showProductDetailPage(): (ProductUIModel) -> Unit
        fun showMoreProducts()
        fun deactivateButton()
        fun activateButton()
        fun updateCartBadgeCount(count: Int)
    }

    interface Presenter {
        val isPossibleLoad: Boolean
        fun loadProducts(): List<ProductUIModel>
        fun getRecentProducts(): List<RecentProductUIModel>
        fun loadProductDetailPage()
        fun loadMoreScroll()
        fun updateCartBadge()
        fun updateProductCartCount(): (ProductUIModel) -> Int
        fun addToCart(): (ProductUIModel) -> Unit
        fun updateCart(): (ProductUIModel, Int) -> Unit
    }
}
