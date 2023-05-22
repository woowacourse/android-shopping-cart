package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.model.Counter
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.UnCheckableCartProductModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val productRepository: ProductRepository,
    private val recentProductIdRepository: RecentProductIdRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {

    private var itemCount = Counter(FIRST_SIZE)

    override fun loadProducts() {
        val products = loadProductsWithSize(PRODUCTS_SIZE)
        val cartProductModels = products.map { getCartProductModel(it) }
        view.setProductModels(cartProductModels)
        loadCartCount()
    }

    private fun loadCartCount() {
        val cartCount = cartRepository.getCartEntities().sumOf { it.count }
        view.setCartCount(cartCount)
    }

    private fun getCartProductModel(product: Product) =
        UnCheckableCartProductModel(
            product.toPresentation(),
            cartRepository.getCartEntity(product.id).count,
        )

    private fun loadProductsWithSize(size: Int): List<Product> {
        val products = productRepository.getProductsWithRange(itemCount.value, size)
        itemCount += size
        return products
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

    override fun addCartProductCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count + COUNT_UNIT
        cartRepository.insertCartProduct(cartProductModel.productModel.id, COUNT_UNIT)
        view.replaceProductModel(
            UnCheckableCartProductModel(cartProductModel.productModel, nextCount),
        )
        loadCartCount()
    }

    override fun subCartProductCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count - COUNT_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        view.replaceProductModel(
            UnCheckableCartProductModel(cartProductModel.productModel, nextCount),
        )
        loadCartCount()
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

    companion object {
        private const val PRODUCTS_SIZE = 20
        private const val RECENT_PRODUCTS_SIZE = 10
        private const val FIRST_SIZE = 0
        private const val COUNT_UNIT = 1
    }
}
