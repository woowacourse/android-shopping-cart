package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun navigateToShoppingCartView()
        fun setUpProductDetailView(product: ProductUiModel)
    }

    interface Presenter {

        fun addToShoppingCart()
    }
}
