package woowacourse.shopping.feature.main

import android.util.Log
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
                val now = LocalDateTime.now()
                recentProductRepository.addRecentProduct(RecentProduct(product, now))
                Log.d("hash","${recentProductRepository.getAll()}}")
                view.showProductDetailScreen(position)
            }
        }
        view.addProducts(products)
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMore(lastProductId: Long) {
        val nextProducts = productRepository.getNextProducts(lastProductId).map { product ->
            product.toPresentation().toItemModel { position ->
                view.showProductDetailScreen(position)
            }
        }
        view.addProducts(nextProducts)
    }
}
