package woowacourse.shopping.feature.main

import com.example.domain.model.RecentProduct
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.ProductUiModel
import java.time.LocalDateTime

class MainPresenter(
    private val view: MainContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository
) : MainContract.Presenter {
    private var products: MutableList<ProductUiModel> = mutableListOf()

    override fun loadProducts() {
        val firstProducts = productRepository.getFirstProducts()
        val productUiModels = firstProducts.map { it.toPresentation() }
        val productItemModels = productUiModels.map { productUiModel ->
            productUiModel.toItemModel { position ->
                addRecentProduct(productUiModel)
                view.showProductDetailScreenByProduct(products[position])
                loadRecent()
            }
        }

        products.addAll(productUiModels)
        view.addProducts(productItemModels)
    }

    override fun moveToCart() {
        view.showCartScreen()
    }

    override fun loadMoreProduct() {
        val lastProductId: Long = products.lastOrNull()?.id ?: 0
        val nextProducts = productRepository.getNextProducts(lastProductId)
        val nextProductUiModels = nextProducts.map { it.toPresentation() }
        val nextProductItemModels = nextProductUiModels.map { product ->
            product.toItemModel { position ->
                addRecentProduct(product)
                view.showProductDetailScreenByProduct(products[position])
                loadRecent()
            }
        }

        products.addAll(nextProductUiModels)
        view.addProducts(nextProductItemModels)
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

    override fun resetProducts() {
        productRepository.resetCache()
        view.addProducts(listOf())
    }

    private fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductRepository.addRecentProduct(recentProduct.copy(dateTime = LocalDateTime.now()))
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
