package woowacourse.shopping.feature.main

import com.example.domain.Product
import woowacourse.shopping.data.product.ProductDbHandler

class MainPresenter(
    val view: MainContract.View,
    val db: ProductDbHandler
) : MainContract.Presenter {

    override fun loadProducts() {
        val products: List<Product> = db.getCartProducts()
        view.setProducts(products)
    }
}
