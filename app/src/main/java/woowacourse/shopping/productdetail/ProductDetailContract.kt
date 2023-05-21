package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun navigateToShoppingCartView()

        fun setUpRecentViewedProduct(product: ProductUiModel?)
    }

    interface Presenter {

        val product: ProductUiModel

        fun setUpView()

        fun addToShoppingCart()
    }
}
