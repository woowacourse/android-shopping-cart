package woowacourse.shopping.shopping

import model.Product
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.database.product.ProductRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel
import java.util.concurrent.CountDownLatch

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository,
    private val productRepository: ProductRepository,
) : ShoppingContract.Presenter {

    private var numberOfReadProduct: Long = 0

    override fun loadProducts() {
        val products = selectProducts()
        val recentViewedProducts = repository.selectRecentViewedProducts().map { it.toUiModel() }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
            showMoreShoppingProducts = ::readMoreShoppingProducts,
        )
    }

    override fun readMoreShoppingProducts() {
        val products = selectProducts()

        view.refreshMoreShoppingProductsView(products)
    }

    private fun selectProducts(): List<ProductUiModel> {
        var products = listOf<ProductUiModel>()
        val latch = CountDownLatch(1)
        productRepository.loadProducts(
            lastProductId = numberOfReadProduct,
            onSuccess = {
                products = synchronizeWithCartProducts(it)
                numberOfReadProduct += it.size
                latch.countDown()
            },
            onFailure = {
                products = listOf()
                latch.countDown()
            },
        )
        latch.await()

        return products
    }

    private fun synchronizeWithCartProducts(products: List<Product>): List<ProductUiModel> {
        val cartProducts = repository.selectAllShoppingCartProducts()
        return products.map { product ->
            val count = cartProducts.find { it.product.id == product.id }?.count ?: 0
            product.toUiModel(count)
        }
    }

    override fun addToRecentViewedProduct(id: Int) {
        repository.insertToRecentViewedProducts(id)
    }

    override fun updateRecentViewedProducts() {
        val recentViewedProducts = repository.selectRecentViewedProducts().map { it.toUiModel() }
        view.refreshRecentViewedProductsView(recentViewedProducts)
    }

    override fun changeShoppingCartProductCount(id: Int, isAdd: Boolean) {
        val cartProducts = repository.selectAllShoppingCartProducts()
        val productCount = cartProducts.find { it.product.id == id }?.count ?: 0

        if (productCount == 1 && !isAdd) {
            repository.deleteFromShoppingCart(id)
        } else if (productCount == 0 && isAdd) {
            repository.insertToShoppingCart(id, 1, true)
        } else {
            val amountToCalculate = if (isAdd) PLUS_AMOUNT else MINUS_AMOUNT
            repository.updateShoppingCartCount(id, productCount + amountToCalculate)
            repository.updateShoppingCartSelection(id, true)
        }
        updateToolbar()
    }

    override fun updateToolbar() {
        var totalCount = 0
        val shoppingCartProducts = repository.selectAllShoppingCartProducts()
        shoppingCartProducts.forEach { totalCount += it.count }
        view.updateToolbar(totalCount)
    }

    override fun refreshView() {
        productRepository.loadProducts(
            lastProductId = 0,
            onSuccess = { view.refreshShoppingProductsView(synchronizeWithCartProducts(it)) },
            onFailure = {},
        )
    }

    companion object {
        private const val PLUS_AMOUNT = 1
        private const val MINUS_AMOUNT = -1
    }
}
