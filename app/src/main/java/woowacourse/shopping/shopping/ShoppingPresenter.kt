package woowacourse.shopping.shopping

import model.RecentViewedProducts
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository,
) : ShoppingContract.Presenter {

    override val recentViewedProducts = RecentViewedProducts(
        products = repository.selectRecentViewedProducts(),
    )
    private var numberOfReadProduct: Int = 0

    override fun loadProducts() {
        val products = selectProducts()
        val recentViewedProducts = recentViewedProducts.values.map { it.toUiModel() }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
            showMoreShoppingProducts = ::readMoreShoppingProducts,
        )
    }

    override fun readMoreShoppingProducts() {
        val products = selectProducts()

        view.refreshShoppingProductsView(products)
    }

    private fun selectProducts(): List<ProductUiModel> {
        val products = repository.selectProducts(
            from = numberOfReadProduct,
            count = COUNT_TO_READ,
        ).map { it.toUiModel() }

        numberOfReadProduct += products.size

        return products
    }

    override fun addToRecentViewedProduct(id: Int) {
        val product = repository.selectProductById(id)
        val removedProduct = recentViewedProducts.add(product)

        removedProduct?.let {
            repository.deleteFromRecentViewedProducts()
        }
        repository.insertToRecentViewedProducts(id)
        view.refreshRecentViewedProductsView(
            toAdd = product.toUiModel(),
            toRemove = removedProduct?.toUiModel(),
        )
    }

    companion object {
        private const val COUNT_TO_READ = 20
    }
}
