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

    override fun loadProducts() {
        val products: List<Product> = productDbHandler.getAll()
        val recentProducts: RecentProducts = recentProductDbHandler.getRecentProducts()
        view.setProducts(products, recentProducts)
    }
}
