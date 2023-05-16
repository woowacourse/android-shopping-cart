package woowacourse.shopping.shoppingcart

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ShoppingCartProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 0

    private fun selectShoppingCartProducts(): List<ShoppingCartProductUiModel> {
        val products = repository.selectShoppingCartProducts(
            from = numberOfReadShoppingCartProduct,
            count = COUNT_TO_READ
        ).map {
            it.toUiModel()
        }
        numberOfReadShoppingCartProduct += COUNT_TO_READ

        return products
    }

    override fun loadShoppingCartProducts() {
        val products = selectShoppingCartProducts()

        view.setUpShoppingCartView(
            products = products,
            onRemoved = ::removeShoppingCartProduct,
            onAdded = ::addShoppingCartProducts,
            onProductCountMinus = ::minusShoppingCartProductCount,
            onProductCountPlus = ::plusShoppingCartProductCount
        )
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
    }

    override fun addShoppingCartProducts() {
        val products = selectShoppingCartProducts()

        view.showMoreShoppingCartProducts(products)
    }

    override fun plusShoppingCartProductCount(product: ShoppingCartProductUiModel) {
        val shoppingCartProduct = product.toDomainModel().plusCount()

        repository.insertToShoppingCart(
            id = shoppingCartProduct.product.id,
            count = shoppingCartProduct.count.value
        )
        view.refreshShoppingCartProductView(shoppingCartProduct.toUiModel())
    }

    override fun minusShoppingCartProductCount(product: ShoppingCartProductUiModel) {
        val shoppingCartProduct = product.toDomainModel().minusCount()

        repository.insertToShoppingCart(
            id = shoppingCartProduct.product.id,
            count = shoppingCartProduct.count.value
        )
        view.refreshShoppingCartProductView(shoppingCartProduct.toUiModel())
    }

    companion object {
        private const val COUNT_TO_READ = 3
    }
}
