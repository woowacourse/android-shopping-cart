package woowacourse.shopping.shopping

import model.Product
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.database.product.ProductRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository,
    private val productRepository: ProductRepository,
) : ShoppingContract.Presenter {

    private var numberOfReadProduct: Long = 0
    private var readCount: Int = 0

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
        var products = listOf<Product>()
        productRepository.loadProducts(
            numberOfReadProduct,
            { products = it },
            { products = listOf() },
        )
/*
    SQLite를 사용했을 때 코드
        val products = repository.selectProducts(
            from = numberOfReadProduct,
            count = COUNT_TO_READ,
        )
*/

        numberOfReadProduct += products.size
        readCount++

        return synchronizeWithCartProducts(products)
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
            0,
            { view.refreshShoppingProductsView(synchronizeWithCartProducts(it)) },
            {},
        )
        Thread.sleep(1000)

/*
    SQLite를 사용했을 때 코드
        val products = repository.selectProducts(
            from = 0,
            count = COUNT_TO_READ * readCount,
        )
        view.refreshShoppingProductsView(synchronizeWithCartProducts(products))
*/
    }

    companion object {
//        private const val COUNT_TO_READ = 20
        private const val PLUS_AMOUNT = 1
        private const val MINUS_AMOUNT = -1
    }
}
