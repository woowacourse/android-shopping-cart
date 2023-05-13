package woowacourse.shopping.feature.main

import com.example.domain.model.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {
    private val products: MutableList<ProductUiModel> = mutableListOf()
    private val recentProducts: MutableList<RecentProductUiModel> = mutableListOf()

    override fun loadProducts() {
        val firstProducts = productRepository.getFirstProducts()
        val productUiModels = firstProducts.map { it.toPresentation() }

        products.addAll(productUiModels)
        view.addProducts(productUiModels)
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct() {
        val lastProductId: Long = products.lastOrNull()?.id ?: 0
        val nextProducts = productRepository.getNextProducts(lastProductId)
        val nextProductUiModels = nextProducts.map { it.toPresentation() }

        products.addAll(nextProductUiModels)
        view.addProducts(nextProductUiModels)
    }

    override fun loadRecent() {
        val recentProductUiModels = recentProductRepository.getAll().map { it.toPresentation() }

        with(recentProducts) {
            clear()
            addAll(recentProductUiModels)
        }
        view.updateRecent(recentProductUiModels)
    }

    override fun showProductDetail(position: Int) {
        view.showProductDetailScreen(products[position])
        addRecentProduct(products[position])
        loadRecent()
    }

    override fun showRecentProductDetail(position: Int) {
        view.showProductDetailScreen(recentProducts[position].productUiModel)
        addRecentProduct(recentProducts[position])
        loadRecent()
    }

    override fun resetProducts() {
        productRepository.resetCache()
        view.addProducts(listOf())
    }

    private fun addRecentProduct(recentProduct: RecentProductUiModel) {
        recentProductRepository.addRecentProduct(
            recentProduct.toDomain().copy(dateTime = LocalDateTime.now())
        )
    }

    private fun addRecentProduct(product: ProductUiModel) {
        recentProductRepository.addRecentProduct(
            RecentProduct(
                product.toDomain(),
                LocalDateTime.now()
            )
        )
    }
}
