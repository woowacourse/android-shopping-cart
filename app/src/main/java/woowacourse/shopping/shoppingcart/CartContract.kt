package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface CartContract {

    interface View {

        fun showMessageReachedEndPage()
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun plusShoppingCartProductCount(id: Int)

        fun minusShoppingCartProductCount(id: Int)

        fun changeProductSelectedState(product: CartProductUiModel, isSelected: Boolean)

        fun changeProductsSelectedState(checked: Boolean)

        fun moveToNextPage()

        fun moveToPrevPage()
    }
}
