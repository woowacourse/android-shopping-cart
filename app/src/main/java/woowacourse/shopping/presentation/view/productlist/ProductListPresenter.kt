package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImp
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImp(),
    private val recentProductRepository: RecentProductRepository
) : ProductContract.Presenter {
    private val products = mutableListOf<ProductModel>()
    private val recentProducts = mutableListOf<RecentProductModel>()
    private var recentProductsPreSize = 0

    override fun initRecentProductItems() {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN))
        recentProductRepository.deleteNotTodayRecentProducts(today)
    }

    override fun loadProductItems() {
        products.addAll(productRepository.getData(0, LOAD_PRODUCT_COUNT))
        view.setProductItemsView(products)
    }

    override fun loadRecentProductItems() {
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        recentProductsPreSize = recentProducts.size
        view.setRecentProductItemsView(recentProducts)
    }

    override fun updateRecentProductItems() {
        recentProducts.clear()
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        val diffSize = recentProducts.size - recentProductsPreSize
        view.updateRecentProductItemsView(recentProductsPreSize, diffSize)
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }

    override fun loadMoreData(startPosition: Int) {
        val newProducts = productRepository.getData(startPosition, LOAD_PRODUCT_COUNT)
        products.addAll(newProducts)
        view.updateMoreProductsView(startPosition + 1, newProducts.size)
    }

    companion object {
        private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
        private const val LOAD_PRODUCT_COUNT = 20
    }
}
