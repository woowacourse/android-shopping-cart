package woowacourse.shopping.shoppingcart

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.util.CART_PRODUCT_TO_READ
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 0

    private fun selectShoppingCartProducts(): List<CartProductUiModel> {
        val cartProducts = repository.selectShoppingCartProducts(
            numberOfReadShoppingCartProduct,
            CART_PRODUCT_TO_READ,
        ).map { it.toUiModel() }
        numberOfReadShoppingCartProduct += CART_PRODUCT_TO_READ

        return cartProducts
    }

    override fun loadShoppingCartProducts() {
        val products = selectShoppingCartProducts()
        val productsSize = repository.getShoppingCartProductsSize()

        view.setUpShoppingCartView(products, ::removeShoppingCartProduct, productsSize)
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
    }

    override fun readMoreShoppingCartProducts() {
        val products = selectShoppingCartProducts()
        view.showMoreShoppingCartProducts(products)
    }
}
