package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.Product
import woowacourse.shopping.Products
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductIdRepository: RecentProductIdRepository,
) : ProductListContract.Presenter {

    private val products = Products()

    override fun initProducts() {
        val receivedProducts = productRepository.getProductsWithRange(products.size, PRODUCTS_SIZE)
        products.addProducts(receivedProducts)
        view.initProductModels(products.toPresentation())
    }

    override fun updateProducts() {
        val receivedProducts = productRepository.getProductsWithRange(products.size, PRODUCTS_SIZE)
        products.addProducts(receivedProducts)
        view.setProductModels(products.toPresentation())
    }

    override fun initRecentProducts() {
        val recentProductModels = getRecentProductModels()
        view.initRecentProductModels(recentProductModels)
    }

    override fun updateRecentProducts() {
        val recentProductModels = getRecentProductModels()
        view.setRecentProductModels(recentProductModels)
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

    private fun productsToPresentation(products: List<Product>): List<ProductModel> {
        return products.map { it.toPresentation() }
    }

    private fun Products.toPresentation(): List<ProductModel> {
        return items.map { it.toPresentation() }
    }

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
    }
}
