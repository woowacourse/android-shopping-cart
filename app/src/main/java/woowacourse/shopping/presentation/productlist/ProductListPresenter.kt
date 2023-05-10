package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.Product
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductIdRepository: RecentProductIdRepository,
) : ProductListContract.Presenter {

    override fun initProducts() {
        val productModels = productsToPresentation(productRepository.products)
        view.initProducts(productModels)
    }

    override fun initRecentProducts() {
        val recentProductModels = getRecentProductModels()
        view.initRecentProducts(recentProductModels)
    }

    override fun updateRecentProducts() {
        val recentProductModels = getRecentProductModels()
        view.updateRecentProducts(recentProductModels)
    }

    override fun saveRecentProductId(productId: Int) {
        recentProductIdRepository.addRecentProductId(productId)
    }

    private fun getRecentProductModels(): List<ProductModel> {
        val recentProductIds = recentProductIdRepository.getRecentProductIds(10)
        return findProductsById(recentProductIds)
    }

    private fun findProductsById(productIds: List<Int>): List<ProductModel> {
        return productIds.map {
            val product = productRepository.findProductById(it) ?: Product.defaultProduct
            product.toPresentation()
        }
    }

    private fun productsToPresentation(products: List<Product>): List<ProductModel> {
        return products.map { it.toPresentation() }
    }
}
