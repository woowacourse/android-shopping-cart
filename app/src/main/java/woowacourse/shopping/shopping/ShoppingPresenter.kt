package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.RecentProductMapper.toViewModel
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.domain.RecentProduct

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val recentProductSize: Int,
    private val productLoadSize: Int,
) : ShoppingContract.Presenter {
    private var productLoadedCount: Int = 0

    init {
        productRepository.initMockData()
        loadMoreProduct()
    }

    override fun resumeView() {
        updateRecentProducts()
    }

    override fun openProduct(productModel: ProductModel) {
        val recentProducts = recentProductRepository.selectAll()
        val recentProduct = recentProducts.makeRecentProduct(productModel.toDomainModel())

        recentProductRepository.insertRecentProduct(recentProduct)

        view.showProductDetail(productModel)
    }

    override fun openCart() {
        view.showCart()
    }

    override fun loadMoreProduct() {
        val loadedProducts = productRepository.selectByRange(productLoadedCount, productLoadSize)
        productLoadedCount += productLoadSize
        view.addProducts(loadedProducts.value.map { it.toViewModel() })
    }

    private fun updateRecentProducts() {
        val recentProducts = recentProductRepository.selectAll()
        val recentProductsDesc =
            recentProducts.getRecentProducts(recentProductSize).value.sortedByDescending(
                RecentProduct::ordinal
            )
        view.updateRecentProducts(recentProductsDesc.map { it.toViewModel() })
    }
}
