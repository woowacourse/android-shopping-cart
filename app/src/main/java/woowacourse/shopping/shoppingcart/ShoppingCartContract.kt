package woowacourse.shopping.shoppingcart

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(products: List<ProductUiModel>)
    }

    interface Presenter {

        fun loadShoppingCartProducts()
    }
}
