package woowacourse.shopping.shoppingcart

import android.content.Context
import woowacourse.shopping.database.ShoppingDBRepository
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.database.product.ShoppingDao
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 0

    private fun selectShoppingCartProducts(): List<ProductUiModel> {
        val products = repository.selectShoppingCartProducts(numberOfReadShoppingCartProduct, 3)
            .map { it.toUiModel() }
        numberOfReadShoppingCartProduct += 3

        return products
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

    companion object {
        fun of(view: ShoppingCartContract.View, context: Context): ShoppingCartPresenter {
            return ShoppingCartPresenter(
                view,
                ShoppingDBRepository(
                    ShoppingDao(context),
                ),
            )
        }
    }
}
