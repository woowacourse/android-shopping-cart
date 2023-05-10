package woowacourse.shopping.shopping

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.util.toUiModel

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository
) : ShoppingContract.Presenter {

    override fun loadProducts() {
        val products = repository.selectProducts().map { it.toUiModel() }
        val recentViewedProducts = repository.selectRecentViewedProducts().map { it.toUiModel() }

        view.setUpShoppingView(
            products = products,
            recentViewedProducts = recentViewedProducts
        )
    }

    override fun addToRecentViewedProduct(id: Int) {
        repository.insertToRecentViewedProducts(id)
    }
}
