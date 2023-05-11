package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(products: List<ProductUiModel>, onRemoved: (id: Int) -> Unit)

        fun showMoreShoppingCartProducts(products: List<ProductUiModel>)
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun readMoreShoppingCartProducts()
    }
}
