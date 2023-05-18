package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(
            products: List<CartProductUiModel>,
            onRemoved: (id: Int) -> Unit,
            totalSize: Int,
        )

        fun showMoreShoppingCartProducts(products: List<CartProductUiModel>)
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun readMoreShoppingCartProducts()
    }
}
