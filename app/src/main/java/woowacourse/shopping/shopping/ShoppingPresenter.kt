package woowacourse.shopping.shopping

import model.RecentViewedProducts
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository
) : ShoppingContract.Presenter {

    override val recentViewedProducts = RecentViewedProducts(
        products = repository.selectRecentViewedProducts()
    )

    override fun loadProducts() {
        val products = repository.selectProducts().map { it.toUiModel() }
        val recentViewedProducts = recentViewedProducts.values.map { it.toUiModel() }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts,
        )
    }

    override fun addToRecentViewedProduct(id: Int) {
        val product = repository.selectProductById(id)
        val removedProduct = recentViewedProducts.add(product)

        removedProduct?.let {
            repository.deleteFromRecentViewedProducts(removedProduct.id)
        }
        repository.insertToRecentViewedProducts(id)
        view.refreshShoppingView(
            toAdd = product.toUiModel(),
            toRemove = removedProduct?.toUiModel()
        )
    }
}
