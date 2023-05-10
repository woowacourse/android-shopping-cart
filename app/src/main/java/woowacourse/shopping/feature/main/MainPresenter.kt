package woowacourse.shopping.feature.main

import com.example.domain.repository.ProductRepository
import woowacourse.shopping.mapper.toPresentation

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository
) : MainContract.Presenter {

    override fun loadProducts() {
        val products = productRepository.getFirstProducts().map { product ->
            product.toPresentation().toItemModel { position ->
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
