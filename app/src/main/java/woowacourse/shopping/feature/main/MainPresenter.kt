package woowacourse.shopping.feature.main

import com.example.domain.Product

class MainPresenter(
    val view: MainContract.View
) : MainContract.Presenter {

    override fun loadProducts() {
        val products: List<Product> = Product.getDummy()
        view.setProducts(products)
    }
}
