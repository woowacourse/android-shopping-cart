package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(
            products: List<CartProductUiModel>,
            totalSize: Int,
        )

        fun showMoreShoppingCartProducts(products: List<CartProductUiModel>)

        fun updateTotalInfo(price: Int, count: Int)

        fun checkAllBtnOrNot()
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun readMoreShoppingCartProducts()

        fun changeShoppingCartProductCount(id: Int, isAdd: Boolean)

        fun changeShoppingCartProductSelection(id: Int, isSelected: Boolean)

        fun checkAllBox(products: List<CartProductUiModel>, isSelected: Boolean)
    }
}
