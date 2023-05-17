package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.model.Counter
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductIdRepository: RecentProductIdRepository,
) : ProductListContract.Presenter {

    private var itemCount = Counter(FIRST_SIZE)

    override fun loadProducts() {
        val receivedProducts = receiveProducts()
        view.setProductModels(receivedProducts.toPresentation())
    }

    private fun receiveProducts(): List<Product> {
        val receivedProducts =
            productRepository.getProductsWithRange(itemCount.value, PRODUCTS_SIZE)
        itemCount += PRODUCTS_SIZE

        return receivedProducts
    }

    override fun loadRecentProducts() {
        val recentProductModels = getRecentProductModels()
        if (recentProductModels.isNotEmpty()) {
            view.setRecentProductModels(recentProductModels)
        }
    }

    override fun saveRecentProductId(productId: Int) {
        recentProductIdRepository.deleteRecentProductId(productId)
        recentProductIdRepository.addRecentProductId(productId)
    }

    private fun getRecentProductModels(): List<ProductModel> {
        val recentProductIds = recentProductIdRepository.getRecentProductIds(RECENT_PRODUCTS_SIZE)
        return findProductsById(recentProductIds)
    }

    private fun findProductsById(productIds: List<Int>): List<ProductModel> {
        return productIds.map {
            val product = productRepository.findProductById(it) ?: Product.defaultProduct
            product.toPresentation()
        }
    }

    private fun List<Product>.toPresentation(): List<ProductModel> {
        return this.map { it.toPresentation() }
    }

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
        private const val FIRST_SIZE = 0
    }
}
