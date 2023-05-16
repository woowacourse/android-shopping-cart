package woowacourse.shopping.shoppingcart

import model.ShoppingCart
import model.ShoppingCartProduct
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ShoppingCartProductUiModel
import woowacourse.shopping.util.toDomainModel
import woowacourse.shopping.util.toUiModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val repository: ShoppingRepository,
) : ShoppingCartContract.Presenter {

    private var numberOfReadShoppingCartProduct: Int = 0
    private var shoppingProducts: MutableList<ShoppingCartProduct> = mutableListOf()

    private fun selectShoppingCartProducts(): List<ShoppingCartProduct> {
        val products = repository.selectShoppingCartProducts(
            from = numberOfReadShoppingCartProduct,
            count = COUNT_TO_READ
        )

        numberOfReadShoppingCartProduct += products.size

        return products
    }

    override fun loadShoppingCartProducts() {
        val products = selectShoppingCartProducts()
        shoppingProducts.addAll(products)

        view.setUpShoppingCartView(
            products = shoppingProducts.map { it.toUiModel() },
            onRemoved = ::removeShoppingCartProduct,
            onAdded = ::addShoppingCartProducts,
            onProductCountMinus = ::minusShoppingCartProductCount,
            onProductCountPlus = ::plusShoppingCartProductCount,
            onTotalPriceChanged = ::onTotalPriceChanged
        )
    }

    override fun removeShoppingCartProduct(id: Int) {
        repository.deleteFromShoppingCart(id)
    }

    override fun addShoppingCartProducts() {
        val products = selectShoppingCartProducts()
        shoppingProducts.addAll(products)

        view.showMoreShoppingCartProducts(products.map { it.toUiModel() })
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

    override fun onTotalPriceChanged(products: List<ShoppingCartProductUiModel>) {
        val totalPrice = ShoppingCart(
            products = products.map { it.toDomainModel() }
        ).totalPrice

        view.setUpTextTotalPriceView(price = totalPrice)
    }

    override fun changeProductsSelectedState(checked: Boolean) {
        shoppingProducts = shoppingProducts.map { it.setSelectedState(checked) }
            .toMutableList()

        view.refreshShoppingCartProductView(shoppingProducts.map { it.toUiModel() })
    }

    companion object {
        private const val COUNT_TO_READ = 3
    }
}
