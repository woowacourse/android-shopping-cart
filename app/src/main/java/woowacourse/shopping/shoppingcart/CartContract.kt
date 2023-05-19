package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface CartContract {

    interface View {

        fun showMessageReachedEndPage()
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(product: CartProductUiModel)

        fun plusShoppingCartProductCount(product: CartProductUiModel)

        fun minusShoppingCartProductCount(product: CartProductUiModel)

        fun changeProductSelectedState(product: CartProductUiModel, isSelected: Boolean)

        fun changeProductsSelectedState(checked: Boolean)

        fun moveToNextPage()

        fun moveToPrevPage()
    }
}
