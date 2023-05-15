package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.recentproduct.RecentProductDbHandler
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.list.item.RecentProductListItem
import woowacourse.shopping.model.mapper.toDomain
import woowacourse.shopping.model.mapper.toItem
import woowacourse.shopping.model.mapper.toRecentProduct
import woowacourse.shopping.model.mapper.toUi

class MainPresenter(
    private val view: MainContract.View,
    private val productDbHandler: ProductDbHandler,
    private val recentProductDbHandler: RecentProductDbHandler
) : MainContract.Presenter {

    private val loadItemCountUnit = 20

    private val products: List<Product> = productDbHandler.getAll()
    private val recentProducts: List<RecentProduct> = recentProductDbHandler.getAll()

    private var loadItemFromIndex = 0
    private val loadItemToIndex: Int
        get() = if (products.size > loadItemFromIndex + loadItemCountUnit) loadItemFromIndex + loadItemCountUnit
        else products.size

    override fun loadMoreProducts() {
        if (products.isEmpty()) {
            view.showEmptyProducts()
            return
        }
        if (loadItemFromIndex == 0) {
            view.setProducts(listOf(), recentProducts)
        }

        view.addProductItems(getAddProductsUnit())
        loadItemFromIndex = loadItemToIndex
    }

    override fun showProductDetail(listItem: ListItem) {
        when (listItem) {
            is ProductListItem -> {
                storeRecentProduct(listItem.toRecentProduct())
                view.showProductDetail(listItem.toUi())
            }
        }
    }

    private fun getAddProductsUnit(): List<ProductListItem> {
        val productsUnit: List<Product> = products.subList(loadItemFromIndex, loadItemToIndex)
        return productsUnit.map { it.toUi().toItem() }
    }

    private fun storeRecentProduct(recentProductListItem: RecentProductListItem) {
        recentProductDbHandler.addColumn(recentProductListItem.toUi().toDomain())
    }
}
