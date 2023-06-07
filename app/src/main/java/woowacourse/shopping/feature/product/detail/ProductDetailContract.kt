package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.feature.product.model.ProductState
import woowacourse.shopping.feature.product.recent.model.RecentProductState

interface ProductDetailContract {

    interface View {
        fun setViewContent(product: ProductState)
        fun setMostRecentViewContent(recentProductState: RecentProductState?)
        fun setDialogCount(count: Int)
        fun showCart()
        fun showAccessError()
        fun showSelectCountDialog(productState: ProductState)
        fun showProductDetail(product: ProductState)
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?
        val recentProduct: RecentProductState?

        fun loadProduct()
        fun loadRecentProduct()
        fun navigateSelectCountDialog()
        fun addCartProduct(count: Int)
        fun plusCount()
        fun minusCount()
        fun navigateProductDetail()
    }
}
