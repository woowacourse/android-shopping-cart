package woowacourse.shopping.ui.shopping.contract.presenter

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import com.example.domain.model.RecentRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.contract.ShoppingContract

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val repository: ProductRepository,
    private val recentRepository: RecentRepository
) : ShoppingContract.Presenter {
    private var productsData: MutableList<ProductsItemType> = mutableListOf()

    override fun setUpProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { product: Product -> ProductsItemType.Product(product.toUIModel()) }
        view.setProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun updateProducts() {
        val recentProductsData = ProductsItemType.RecentProducts(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() }
        )

        when {
            productsData[0] is ProductsItemType.RecentProducts -> productsData[0] = recentProductsData
            recentProductsData.product.isNotEmpty() -> productsData.add(0, recentProductsData)
        }

        view.setProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun fetchMoreProducts() {
        productsData += repository.getNext(PRODUCT_COUNT)
            .map { ProductsItemType.Product(it.toUIModel()) }
        view.addProducts(productsData.plus(ProductsItemType.ReadMore))
    }

    override fun navigateToItemDetail(data: ProductUIModel) {
        view.navigateToProductDetail(data)
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
