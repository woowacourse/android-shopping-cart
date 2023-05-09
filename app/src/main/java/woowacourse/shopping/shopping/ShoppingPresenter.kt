package woowacourse.shopping.shopping

import woowacourse.shopping.database.ShoppingRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ShoppingRepository
) : ShoppingContract.Presenter {

    override fun loadProducts() {
        val products = repository.loadProducts()

        view.setUpShoppingView(products)
    }
}
