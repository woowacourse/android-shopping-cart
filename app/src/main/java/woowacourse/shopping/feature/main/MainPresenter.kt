package woowacourse.shopping.feature.main

import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.CartRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.ProductUiModel
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository
) : MainContract.Presenter {

    override fun loadProducts() {
        val firstProducts = productRepository.getFirstProducts()

        val productItems = matchCartProductCount(firstProducts)
        view.addProducts(productItems)
    }

    private fun matchCartProductCount(products: List<Product>): List<ProductUiModel> {
        val cartProducts = cartRepository.getAll()
        return products.map { product ->
            val count = cartProducts.find { it.product.id == product.id }?.count ?: 0
            product.toPresentation(count)
        }
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun moveToDetail(product: ProductUiModel) {
        addRecentProduct(RecentProduct(product.toDomain(), LocalDateTime.now()))
        view.showProductDetailScreenByProduct(product)
        loadRecent()
    }

    override fun loadMoreProduct() {
        val nextProducts = productRepository.getNextProducts()
        val nextProductItems = matchCartProductCount(nextProducts)
        view.addProducts(nextProductItems)
    }

    override fun loadRecent() {
        val recent = recentProductRepository.getAll().map { it.toPresentation() }
        view.updateRecent(recent)
    }

    override fun refresh() {
        productRepository.clearCache()
    }

    private fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct.copy(dateTime = LocalDateTime.now()))
    }
}
