package woowacourse.shopping.ui.shopping.contract.presenter

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import com.example.domain.model.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.ProductItem
import woowacourse.shopping.ui.shopping.ProductReadMore
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.RecentProductsItem
import woowacourse.shopping.ui.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { product: Product -> ProductItem(product.toUIModel()) }
        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun updateProducts() {
        val recentProductsData = RecentProductsItem(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() }
        )

        when {
            productsData[0] is RecentProductsItem -> productsData[0] = recentProductsData
            recentProductsData.product.isNotEmpty() -> productsData.add(0, recentProductsData)
        }

        view.setProducts(productsData.plus(ProductReadMore))
    }

    override fun fetchMoreProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { ProductItem(it.toUIModel()) }
        view.addProducts(productsData.plus(ProductReadMore))
    }

    override fun navigateToItemDetail(data: ProductUIModel) {
        view.navigateToProductDetail(data)
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
