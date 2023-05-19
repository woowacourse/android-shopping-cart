package woowacourse.shopping.feature.main

import android.util.Log
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
import java.util.logging.Handler

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {
    private val products: MutableList<ProductUiModel> = mutableListOf()
    private val recentProducts: MutableList<RecentProductUiModel> = mutableListOf()

    override fun loadProducts() {
        productRepository.getFirstProducts(
            onSuccess = {
                val productUiModels = makeProductUiModels(it)
                products.clear()
                products.addAll(productUiModels)

                view.setProducts(products.toList())
                updateCartCountBadge()
            },
            onFailure = {
                view.hideLoadMore()
            }
        )
    }

    private fun makeProductUiModels(products: List<Product>): List<ProductUiModel> {
        val cartProducts = cartRepository.getAll().map { it.toPresentation() }
        val productUiModels = products.map { product ->
            val findCartProduct = cartProducts.find { cartProduct ->
                product.id == cartProduct.productUiModel.id
            } ?: return@map product.toPresentation()
            product.toPresentation().apply { this.count = findCartProduct.productUiModel.count }
        }
        return productUiModels
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct() {
        val lastProductId: Long = products.lastOrNull()?.id ?: 0
        val nextProducts = productRepository.getNextProducts(
            lastProductId,
            onSuccess = {
                val nextProductUiModels = makeProductUiModels(it)
                products.addAll(nextProductUiModels)

                view.setProducts(products.toList())
            },
            onFailure = {
                view.hideLoadMore()
            }
        )
    }

    override fun loadRecent() {
        val recentProductUiModels = recentProductRepository.getAll().map { it.toPresentation() }

        with(recentProducts) {
            clear()
            addAll(recentProductUiModels)
        }
        view.updateRecent(recentProductUiModels)
    }

    override fun loadCartCountSize() {
        updateCartCountBadge()
    }

    override fun showProductDetail(productId: Long) {
        val product = products.find { it.id == productId } ?: return
        val recentProduct = recentProducts.firstOrNull()
        view.showProductDetailScreen(product, recentProduct)
        addRecentProduct(product)
        loadRecent()
    }

    override fun showRecentProductDetail(productId: Long) {
        val recentClickProduct = recentProducts.find { it.productUiModel.id == productId } ?: return
        val recentProduct = recentProducts.firstOrNull()
        view.showProductDetailScreen(recentClickProduct.productUiModel, recentProduct)
        addRecentProduct(recentClickProduct)
        loadRecent()
    }

    override fun changeProductCartCount(productId: Long, count: Int) {
        val product = products.find { it.id == productId } ?: return
        product.count = count
        cartRepository.changeCartProductCount(product.toDomain(), count)

        view.setProducts(products.toList())
        updateCartCountBadge()
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

    private fun updateCartCountBadge() {
        val allCount = cartRepository.getAllCountSize()
        if (allCount > 0) {
            view.showCartCountBadge()
        } else {
            view.hideCartCountBadge()
        }
        view.updateCartCount(allCount)
    }
}
