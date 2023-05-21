package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun navigateToShoppingCartView()

        fun setUpRecentViewedProduct(product: ProductUiModel?)

        fun showCountProductView()
    }

    interface Presenter {

        val product: ProductUiModel

        fun setUpView()

        fun onClickShoppingCartBtn()

        fun changeCount(isAdd: Boolean)

        fun addToShoppingCart()
    }
}
