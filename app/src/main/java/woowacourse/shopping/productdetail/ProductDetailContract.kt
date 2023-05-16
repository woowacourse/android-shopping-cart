package woowacourse.shopping.productdetail

import woowacourse.shopping.model.ProductUiModel

interface ProductDetailContract {

    interface View {

        fun navigateToShoppingCartView()
    }

    interface Presenter {

        val product: ProductUiModel

        fun addToShoppingCart()
    }
}
