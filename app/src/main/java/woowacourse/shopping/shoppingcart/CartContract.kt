package woowacourse.shopping.shoppingcart

interface CartContract {

    interface View {

        fun showMessageReachedEndPage()
        fun setUpOrderButtonText(cartProductsCount: Int)
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun plusShoppingCartProductCount(id: Int)

        fun minusShoppingCartProductCount(id: Int)

        fun changeProductSelectedState(id: Int, isSelected: Boolean)

        fun changeProductsSelectedState(checked: Boolean)

        fun moveToNextPage()

        fun moveToPrevPage()
    }
}
