package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImp
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.model.RecentProductModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImp(),
    private val recentProductRepository: RecentProductRepository
) : ProductContract.Presenter {
    private val recentProducts = mutableListOf<RecentProductModel>()
    private var preSize = 0

    override fun initRecentProductItems() {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN))
        recentProductRepository.deleteNotTodayRecentProducts(today)
    }

    override fun loadProductItems() {
        val products = productRepository.getData()
        view.setProductItemsView(products)
    }

    override fun loadRecentProductItems() {
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        preSize = recentProducts.size
        view.setRecentProductItemsView(recentProducts)
    }

    override fun updateRecentProductItems() {
        recentProducts.clear()
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        val diffSize = recentProducts.size - preSize
        view.updateRecentProductItemsView(preSize, diffSize)
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }

    companion object {
        private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
    }
}
