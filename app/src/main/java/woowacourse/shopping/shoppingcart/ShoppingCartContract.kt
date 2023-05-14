package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ShoppingCartProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(
            products: List<ShoppingCartProductUiModel>,
            onRemoved: (id: Int) -> Unit,
            onAdded: () -> (Unit)
        )

        fun showMoreShoppingCartProducts(products: List<ShoppingCartProductUiModel>)
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun addShoppingCartProducts()
    }
}
