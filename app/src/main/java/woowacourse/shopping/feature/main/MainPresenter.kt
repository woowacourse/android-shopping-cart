package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProducts
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler

class MainPresenter(
    val view: MainContract.View,
    val productDbHandler: ProductDbHandler,
    val recentProductDbHandler: RecentProductDbHandler
) : MainContract.Presenter {
    private var currentItemIndex = 0

    override fun loadProducts() {
        val products: List<Product> =
            productDbHandler.getAll().subList(currentItemIndex, currentItemIndex + ADD_SIZE)
        currentItemIndex += ADD_SIZE
        val recentProducts: RecentProducts = recentProductDbHandler.getRecentProducts()
        view.setProducts(products, recentProducts)
    }

    override fun addProducts() {
        val products: List<Product> = productDbHandler.getAll()
        val addItems: List<Product>
        if (products.size < currentItemIndex + ADD_SIZE) {
            addItems = products.subList(currentItemIndex, products.size - 1)
            currentItemIndex += products.size - 1 - currentItemIndex
        } else {
            addItems = products.subList(currentItemIndex, currentItemIndex + ADD_SIZE)
            currentItemIndex += ADD_SIZE
        }
        view.addProducts(addItems)
    }

    companion object {
        private const val ADD_SIZE = 20
    }
}
