package woowacourse.shopping.shopping.contract.presenter

import com.domain.model.ProductRepository
import com.domain.model.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.shopping.ProductItem
import woowacourse.shopping.shopping.ProductReadMore
import woowacourse.shopping.shopping.ProductsItemType
import woowacourse.shopping.shopping.RecentProductsItem
import woowacourse.shopping.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productOffset: Int,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository,
) : ShoppingContract.Presenter {
    private val recentProductsData: RecentProductsItem
        get() = RecentProductsItem(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() },
        )

    override fun setUpProducts() {
        val productsData = mutableListOf<ProductsItemType>().apply {
            if (recentProductsData.product.isNotEmpty()) {
                add(recentProductsData)
            }
            addAll(repository.getUntil(productOffset).map { ProductItem(it.toUIModel()) })
            add(ProductReadMore)
        }
        view.setProducts(productsData)
    }

    override fun updateRecentProducts() {
        view.addRecentProducts(recentProductsData)
    }

    override fun fetchMoreProducts() {
        view.addProducts(
            repository.getNext(PRODUCT_COUNT).map { ProductItem(it.toUIModel()) },
        )
    }

    override fun navigateToItemDetail(data: ProductUIModel) {
        view.navigateToProductDetail(data)
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
