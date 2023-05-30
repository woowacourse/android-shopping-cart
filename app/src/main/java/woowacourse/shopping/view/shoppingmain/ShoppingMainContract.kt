package woowacourse.shopping.view.shoppingmain

import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

interface ShoppingMainContract {
    interface View {
        var presenter: Presenter
        fun showProductDetailPage(): (ProductUIModel) -> Unit
        fun showMoreProducts(products: List<ProductUIModel>)
        fun deactivateButton()
        fun activateButton()
        fun updateCartBadgeCount(count: Int)
    }

    interface Presenter {
        val isPossibleLoad: Boolean
        fun loadProducts()
        fun getRecentProducts(): List<RecentProductUIModel>
        fun loadProductDetailPage()
        fun loadMoreScroll()
        fun updateCartBadge()
        fun updateProductCartCount(productUIModel: ProductUIModel): Int
        fun addToCart(productUIModel: ProductUIModel)
        fun updateCart(productUIModel: ProductUIModel, count: Int)
    }
}
