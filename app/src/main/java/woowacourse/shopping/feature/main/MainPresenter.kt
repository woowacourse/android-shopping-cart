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

    private val loadItemCountUnit = 20

    private val products: List<Product> = productDbHandler.getAll()
    private val recentProducts: List<RecentProduct> = recentProductDbHandler.getAll()
    private var loadMoreItemStartIndex = 0

    override fun loadMoreProducts() {
        val addItems: List<Product>

        if (products.isEmpty()) {
            view.showEmptyProducts()
            return
        }
        if (loadMoreItemStartIndex == 0) {
            loadProducts()
            return
        }
        if (products.size < loadMoreItemStartIndex + loadItemCountUnit) {
            addItems = products.subList(loadMoreItemStartIndex, products.size - 1)
            loadMoreItemStartIndex += products.size - 1 - loadMoreItemStartIndex
        } else {
            addItems =
                products.subList(loadMoreItemStartIndex, loadMoreItemStartIndex + loadItemCountUnit)
            loadMoreItemStartIndex += loadItemCountUnit
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
        val productUnit: List<Product> =
            products.subList(loadMoreItemStartIndex, loadMoreItemStartIndex + loadItemCountUnit)

        view.setProducts(productUnit, recentProducts)
        loadMoreItemStartIndex += loadItemCountUnit
    }
}
