package woowacourse.shopping.shoppingcart

import model.CartPagination
import woowacourse.shopping.model.CartProductUiModel

interface CartContract {

    interface View {

        fun setUpCartView(products: List<CartProductUiModel>, currentPage: Int)

        fun setUpTextTotalPriceView(price: Int)

        fun refreshCartProductView(products: List<CartProductUiModel>)

        fun setUpTextPageNumber(pageNumber: Int)

        fun showMessageReachedEndPage()
    }

    interface Presenter {

        val cartPage: CartPagination

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(product: CartProductUiModel)

        fun plusShoppingCartProductCount(product: CartProductUiModel)

        fun minusShoppingCartProductCount(product: CartProductUiModel)

        fun calcTotalPrice()

        fun changeProductSelectedState(product: CartProductUiModel, isSelected: Boolean)

        fun changeProductsSelectedState(checked: Boolean)

        fun moveToNextPage()

        fun moveToPrevPage()
    }
}
