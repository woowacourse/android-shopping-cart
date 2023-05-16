package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.ShoppingCartProductUiModel

interface ShoppingCartContract {

    interface View {

        fun setUpShoppingCartView(
            // todo: 관련 있는 것들끼리 interface로 묶어서 인자 수를 줄이기
            products: List<ShoppingCartProductUiModel>,
            onRemoved: (id: Int) -> Unit,
            onAdded: () -> Unit,
            onProductCountPlus: (product: ShoppingCartProductUiModel) -> Unit,
            onProductCountMinus: (product: ShoppingCartProductUiModel) -> Unit
        )

        fun showMoreShoppingCartProducts(products: List<ShoppingCartProductUiModel>)

        fun refreshShoppingCartProductView(product: ShoppingCartProductUiModel)
    }

    interface Presenter {

        fun loadShoppingCartProducts()

        fun removeShoppingCartProduct(id: Int)

        fun addShoppingCartProducts()

        fun plusShoppingCartProductCount(product: ShoppingCartProductUiModel)

        fun minusShoppingCartProductCount(product: ShoppingCartProductUiModel)
    }
}
