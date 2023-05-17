package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ShoppingCartProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(products: List<ShoppingCartProductUiModel>, currentPage: Int)

        fun setUpTextTotalPriceView(price: Int)

        fun refreshShoppingCartProductView(products: List<ShoppingCartProductUiModel>)

        fun setUpTextPageNumber(pageNumber: Int)

        fun showMessageReachedEndPage()
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(product: ShoppingCartProductUiModel)

        fun plusShoppingCartProductCount(product: ShoppingCartProductUiModel)

        fun minusShoppingCartProductCount(product: ShoppingCartProductUiModel)

        fun calcTotalPrice()

        fun changeProductSelectedState(product: ShoppingCartProductUiModel, isSelected: Boolean)

        fun changeProductsSelectedState(checked: Boolean)

        fun moveToNextPage()

        fun moveToPrevPage()
    }
}
