package woowacourse.shopping.shopping

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingContract {

    interface View {

        fun setUpShoppingView(products: List<ProductUiModel>)
    }

    interface Presenter {

        fun loadProducts()
    }
}
