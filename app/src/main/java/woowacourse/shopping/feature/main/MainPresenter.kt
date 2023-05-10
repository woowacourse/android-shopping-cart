package woowacourse.shopping.feature.main

import com.example.domain.model.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toPresentation
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {

    override fun loadProducts() {
        val products = productRepository.getFirstProducts().map { product ->
            product.toPresentation().toItemModel { position ->
                addRecentProduct(RecentProduct(product, LocalDateTime.now()))
                view.showProductDetailScreenByProduct(position)
                loadRecent()
            }
        }
        view.addProducts(products)
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct(lastProductId: Long) {
        val nextProducts = productRepository.getNextProducts(lastProductId).map { product ->
            product.toPresentation().toItemModel { position ->
                addRecentProduct(RecentProduct(product, LocalDateTime.now()))
                view.showProductDetailScreenByProduct(position)
                loadRecent()
            }
        }
        view.addProducts(nextProducts)
    }

    override fun loadRecent() {
        val recent = recentProductRepository.getAll().map {
            it.toPresentation().toItemModel { position ->
                addRecentProduct(it)
                view.showProductDetailScreenByRecent(position)
                loadRecent()
            }
        }
        view.updateRecent(recent)
    }

    private fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct.copy(dateTime = LocalDateTime.now()))
    }
}
