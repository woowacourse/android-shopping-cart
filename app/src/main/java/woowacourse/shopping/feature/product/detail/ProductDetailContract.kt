package woowacourse.shopping.feature.product.detail

import woowacourse.shopping.databinding.DialogSelectCountBinding
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState

interface ProductDetailContract {

    interface View {
        fun setViewContent(product: ProductState)
        fun setMostRecentViewContent(recentProductState: RecentProductState?)
        fun setCount(selectCountDialogBinding: DialogSelectCountBinding, count: Int)
        fun showCart()
        fun showAccessError()
        fun showSelectCountDialog()
        fun showProductDetail(product: ProductState)
        fun closeProductDetail()
    }

    interface Presenter {
        val product: ProductState?
        val recentProduct: RecentProductState?

        fun loadProduct()
        fun loadRecentProduct()
        fun selectCount()
        fun addCartProduct(count: Int)
        fun plusCount(selectCountDialogBinding: DialogSelectCountBinding)
        fun minusCount(selectCountDialogBinding: DialogSelectCountBinding)
        fun navigateProductDetail()
    }
}
