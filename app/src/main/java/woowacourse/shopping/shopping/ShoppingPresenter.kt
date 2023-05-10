package woowacourse.shopping.shopping

import woowacourse.shopping.database.ShoppingRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository
) : ShoppingContract.Presenter {

    override fun loadProducts() {
        val products = repository.selectProducts()
        val recentViewedProducts = repository.selectRecentViewedProducts()

        view.setUpShoppingView(products = products, recentViewedProducts = recentViewedProducts)
    }

    override fun addToRecentViewedProduct(id: Int) {
        repository.insertToRecentViewedProducts(id)
    }
}
