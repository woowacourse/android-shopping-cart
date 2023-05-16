package woowacourse.shopping.feature.main

import com.example.domain.model.Product
import com.example.domain.model.RecentProduct
import com.example.domain.repository.CartRepository
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
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {
    private val products: MutableList<ProductUiModel> = mutableListOf()
    private val recentProducts: MutableList<RecentProductUiModel> = mutableListOf()

    override fun loadProducts() {
        val firstProducts = productRepository.getFirstProducts()
        val productUiModels = makeProductUiModels(firstProducts)
        products.clear()
        products.addAll(productUiModels)
        view.setProducts(products.toList())
    }

    private fun makeProductUiModels(products: List<Product>): List<ProductUiModel> {
        val cartProducts = cartRepository.getAll().map { it.toPresentation() }
        val productUiModels = products.map { product ->
            val findCartProduct = cartProducts.find { cartProduct ->
                product.id == cartProduct.productUiModel.id
            } ?: return@map product.toPresentation()
            product.toPresentation().apply { this.count = findCartProduct.count }
        }
        return productUiModels
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct() {
        val lastProductId: Long = products.lastOrNull()?.id ?: 0
        val nextProducts = productRepository.getNextProducts(lastProductId)
        val nextProductUiModels = makeProductUiModels(nextProducts)

        products.addAll(nextProductUiModels)
        view.setProducts(products.toList())
    }

    override fun loadRecent() {
        val recentProductUiModels = recentProductRepository.getAll().map { it.toPresentation() }

        with(recentProducts) {
            clear()
            addAll(recentProductUiModels)
        }
        view.updateRecent(recentProductUiModels)
    }

    override fun showProductDetail(productId: Long) {
        val product = products.find { it.id == productId } ?: return
        view.showProductDetailScreen(product)
        addRecentProduct(product)
        loadRecent()
    }

    override fun showRecentProductDetail(productId: Long) {
        val recentProduct = recentProducts.find { it.productUiModel.id == productId } ?: return
        view.showProductDetailScreen(recentProduct.productUiModel)
        addRecentProduct(recentProduct)
        loadRecent()
    }

    override fun changeProductCartCount(productId: Long, count: Int) {
        val product = products.find { it.id == productId } ?: return
        product.count = count
        cartRepository.changeCartProductCount(product.toDomain(), count)
        view.setProducts(products.toList())
    }

    override fun resetProducts() {
        productRepository.resetCache()
        view.setProducts(listOf())
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
