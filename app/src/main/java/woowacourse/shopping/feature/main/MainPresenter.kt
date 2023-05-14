package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.list.item.RecentProductListItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toRecentProduct
import woowacourse.shopping.feature.model.mapper.toUi

class MainPresenter(
    private val view: MainContract.View,
    private val productDbHandler: ProductDbHandler,
    private val recentProductDbHandler: RecentProductDbHandler
) : MainContract.Presenter {

    private val products: List<Product> = productDbHandler.getAll()
    private var currentItemIndex = 0

    override fun addProducts() {
        val addItems: List<Product>
        if (currentItemIndex == 0) {
            loadProducts()
            return
        }
        if (products.size < currentItemIndex + ADD_SIZE) {
            addItems = products.subList(currentItemIndex, products.size - 1)
            currentItemIndex += products.size - 1 - currentItemIndex
        } else {
            addItems = products.subList(currentItemIndex, currentItemIndex + ADD_SIZE)
            currentItemIndex += ADD_SIZE
        }
        view.addProducts(addItems)
    }

    override fun showProductDetail(listItem: ListItem) {
        when (listItem) {
            is ProductListItem -> {
                storeRecentProduct(listItem.toRecentProduct())
                view.showProductDetail(listItem.toUi())
            }
        }
    }

    private fun storeRecentProduct(recentProductListItem: RecentProductListItem) {
        recentProductDbHandler.addColumn(recentProductListItem.toUi().toDomain())
    }

    private fun loadProducts() {
        val products: List<Product> =
            productDbHandler.getAll().subList(currentItemIndex, currentItemIndex + ADD_SIZE)
        currentItemIndex += ADD_SIZE
        val recentProducts: List<RecentProduct> = recentProductDbHandler.getAll()
        view.setProducts(products, recentProducts)
    }

    companion object {
        private const val ADD_SIZE = 20
    }
}
