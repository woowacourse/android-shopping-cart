package woowacourse.shopping.shoppingcart

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(products: List<ProductUiModel>, onRemoved: (id: Int) -> Unit)
    }

    interface Presenter {

        fun loadShoppingCartProducts()
        fun removeShoppingCartProduct(id: Int)
    }
}
