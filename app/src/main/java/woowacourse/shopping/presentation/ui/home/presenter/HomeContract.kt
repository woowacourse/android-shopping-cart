package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.presentation.ui.common.BaseView
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView

interface HomeContract {
    interface View : BaseView<Presenter> {
        fun setUpProductsOnHome(products: List<ProductsByView>)
        fun setUpMoreProducts(products: List<ProductsByView>)
    }

    interface Presenter {
        fun fetchAllProductsOnHome()
        fun fetchMoreProducts()
    }
}
