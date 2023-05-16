package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.R
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val recentProductRepository: RecentProductRepository
) : ProductContract.Presenter {
    private val products = mutableListOf<ProductModel>()
    private val recentProducts = mutableListOf<RecentProductModel>()

    override fun initRecentProductItems() {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN))
        recentProductRepository.deleteNotTodayRecentProducts(today)
    }

    override fun loadProductItems() {
        products.addAll(productRepository.getData(LOAD_PRODUCT_START_POSITION, LOAD_PRODUCT_COUNT).map { it.toUIModel() })
        view.setProductItemsView(products)
    }

    override fun loadRecentProductItems() {
        recentProducts.addAll(recentProductRepository.getRecentProducts().map { it.toUIModel() })
        view.setRecentProductItemsView(recentProducts)
    }

    override fun updateRecentProductItems() {
        recentProducts.clear()
        recentProducts.addAll(recentProductRepository.getRecentProducts().map { it.toUIModel() })
        view.updateRecentProductItemsView(0, recentProducts.size)
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }

    override fun loadMoreData() {
        val startPosition = products.size
        val newProducts = productRepository.getData(startPosition, LOAD_PRODUCT_COUNT).map { it.toUIModel() }
        products.addAll(newProducts)
        view.updateMoreProductsView(startPosition, newProducts.size)
    }

    override fun actionOptionItem(itemId: Int) {
        when (itemId) {
            R.id.action_cart -> view.moveToCartView()
        }
    }

    companion object {
        private const val LOAD_PRODUCT_START_POSITION = 0
        private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
        private const val LOAD_PRODUCT_COUNT = 20
    }
}
