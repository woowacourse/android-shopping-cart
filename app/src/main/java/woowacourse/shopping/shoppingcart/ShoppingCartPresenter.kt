package woowacourse.shopping.shoppingcart

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository
) : ShoppingCartContract.Presenter {

    override fun loadShoppingCartProducts() {
        val products = repository.selectShoppingCartProducts().map { it.toUiModel() }
        view.setUpShoppingCartView(products, ::removeShoppingCartProduct)
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
    }
}
