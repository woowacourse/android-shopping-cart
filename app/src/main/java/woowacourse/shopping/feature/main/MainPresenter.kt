package woowacourse.shopping.feature.main

import com.example.domain.ProductCache
import com.example.domain.model.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toPresentation
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productCache: ProductCache
) : MainContract.Presenter {

    override fun loadProducts() {
        if (productCache.productList.isEmpty()) {
            loadNewProducts()
        } else {
            loadProductsFromCache()
        }
    }

    private fun loadNewProducts() {
        val firstProducts = productRepository.getFirstProducts()
        val productItems = firstProducts.map { product ->
            product.toPresentation().toItemModel { productUiModel ->
                addRecentProduct(RecentProduct(product, LocalDateTime.now()))
                view.showProductDetailScreenByProduct(productUiModel)
                loadRecent()
            }
        }
        view.addProducts(productItems)
        ProductCache.addProducts(firstProducts)
    }

    override fun loadProductsFromCache() {
        val cacheProducts = ProductCache.productList
        val cacheItems = cacheProducts.map { product ->
            product.toPresentation().toItemModel { productUiModel ->
                addRecentProduct(RecentProduct(product, LocalDateTime.now()))
                view.showProductDetailScreenByProduct(productUiModel)
                loadRecent()
            }
        }
        view.addProducts(cacheItems)
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct(lastProductId: Long) {
        val nextProducts = productRepository.getNextProducts(lastProductId)
        val nextProductItems = nextProducts.map { product ->
            product.toPresentation().toItemModel { productUiModel ->
                addRecentProduct(RecentProduct(product, LocalDateTime.now()))
                view.showProductDetailScreenByProduct(productUiModel)
                loadRecent()
            }
        }
        view.addProducts(nextProductItems)
        ProductCache.addProducts(nextProducts)
    }

    override fun loadRecent() {
        val recent = recentProductRepository.getAll().map {
            it.toPresentation().toItemModel { recentProduct ->
                addRecentProduct(it)
                view.showProductDetailScreenByRecent(recentProduct)
                loadRecent()
            }
        }
        view.updateRecent(recent)
    }

    override fun clearCache() {
        productCache.clear()
    }

    private fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct.copy(dateTime = LocalDateTime.now()))
    }
}
