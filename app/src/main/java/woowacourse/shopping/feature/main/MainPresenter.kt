package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.mapper.toDomain
import woowacourse.shopping.model.mapper.toItem
import woowacourse.shopping.model.mapper.toRecentProduct
import woowacourse.shopping.model.mapper.toUi
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {

    private val loadItemCountUnit = 20

    private val products: List<Product> = productRepository.getAll()
    private val recentProducts: List<RecentProduct> = recentProductRepository.getAll()

    private var loadItemFromIndex = 0
    private val loadItemToIndex: Int
        get() = if (products.size > loadItemFromIndex + loadItemCountUnit) loadItemFromIndex + loadItemCountUnit
        else products.size

    override fun loadMoreProducts() {
        if (products.isEmpty()) {
            view.showEmptyProducts()
            return
        }
        if (loadItemFromIndex == 0) view.setProducts(listOf())

        view.addProductItems(getAddProductsUnit())
        loadItemFromIndex = loadItemToIndex
    }

    override fun addRecentProduct(product: Product) {
        val nowDateTime: LocalDateTime = LocalDateTime.now()
        storeRecentProduct(product.toRecentProduct(nowDateTime))
        view.addRecentProductItems(listOf(product.toRecentProduct(nowDateTime).toUi()))
    }

    override fun loadRecentProducts() {
        view.setRecentProducts(recentProducts)
    }

    override fun showProductDetail(listItem: ListItem) {
        when (listItem) {
            is ProductListItem -> {
                addRecentProduct(listItem.toUi().toDomain())
                view.showProductDetail(listItem.toUi())
            }
        }
    }

    private fun getAddProductsUnit(): List<ProductListItem> {
        val productsUnit: List<Product> = products.subList(loadItemFromIndex, loadItemToIndex)
        return productsUnit.map { it.toUi().toItem() }
    }

    private fun storeRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct)
    }
}
