package woowacourse.shopping.feature.main

import com.example.domain.Product
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.feature.list.item.ProductView
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi

class MainPresenter(
    val view: MainContract.View,
    private val productDb: ProductDbHandler,
    private val recentProductDb: RecentProductDbHandler,
) : MainContract.Presenter {

    private val products: List<Product> = productDb.getAll()
    private var currentItemIndex = 0

    override fun addProducts() {
        if (currentItemIndex == 0) {
            loadProducts()
            return
        }
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

    private fun loadProducts() {
        val products: List<ProductView.ProductItem> =
            productDb.getAll().subList(currentItemIndex, currentItemIndex + ADD_SIZE).map { product ->
                product.toUi().toItem()
            }
        currentItemIndex += ADD_SIZE
        val recentProducts: ProductView.RecentProductsItem = recentProductDb.getRecentProducts().toItem()
        view.setProducts(products, recentProducts)
    }

    override fun storeRecentProduct(product: ProductView.ProductItem) {
        recentProductDb.addColumn(product.toUi().toDomain())
    }

    companion object {
        private const val ADD_SIZE = 20
    }
}
