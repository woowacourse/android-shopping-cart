package woowacourse.shopping.presentation.productlist

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.recentproduct.RecentProductIdRepository
import woowacourse.shopping.model.Counter
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.UnCheckableCartProductModel

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val cartRepository: CartRepository,
    private val recentProductIdRepository: RecentProductIdRepository,
) : ProductListContract.Presenter {

    private var itemCount = Counter(FIRST_SIZE)

    override fun loadProducts() {
        val cartProductModels = loadProductsWithSize(PRODUCTS_SIZE)
        view.setProductModels(cartProductModels)
        loadCartCount()
    }

    private fun loadCartCount() {
        val cartCount = cartRepository.getCartProducts().sumOf { it.quantity }
        view.setCartCount(cartCount)
    }

    private fun getCartProductModel(product: Product): CartProductModel {
        val cartProduct = cartRepository.getCartProduct(product.id)

        return UnCheckableCartProductModel(
            cartProduct.cartId,
            product.toPresentation(),
            cartProduct.quantity,
        )
    }

    private fun loadProductsWithSize(size: Int): List<CartProductModel> {
        val cartProducts = cartRepository.getProductsByRange(itemCount.value, size)
        itemCount += size
        return cartProducts
    }

    override fun loadRecentProducts() {
        val recentProductModels = getRecentProductModels()
        if (recentProductModels.isNotEmpty()) {
            view.setRecentProductModels(recentProductModels)
        }
    }

    override fun saveRecentProductId(productId: Long) {
        recentProductIdRepository.deleteRecentProductId(productId)
        recentProductIdRepository.addRecentProductId(productId)
    }

    override fun addCartProductCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count + COUNT_UNIT
        cartRepository.insertCartProduct(cartProductModel.productModel.id, COUNT_UNIT)
        view.replaceProductModel(
            UnCheckableCartProductModel(
                cartProductModel.cartId,
                cartProductModel.productModel,
                nextCount,
            ),
        )
        loadCartCount()
    }

    override fun subCartProductCount(cartProductModel: CartProductModel) {
        val nextCount = cartProductModel.count - COUNT_UNIT
        cartRepository.updateCartProductCount(cartProductModel.productModel.id, nextCount)
        view.replaceProductModel(
            UnCheckableCartProductModel(
                cartProductModel.cartId,
                cartProductModel.productModel,
                nextCount,
            ),
        )
        loadCartCount()
    }

    private fun getRecentProductModels(): List<ProductModel> {
        val recentProductIds = recentProductIdRepository.getRecentProductIds(RECENT_PRODUCTS_SIZE)
        return findProductsById(recentProductIds)
    }

    private fun findProductsById(productIds: List<Long>): List<ProductModel> {
        return productIds.map {
            val product = cartRepository.findProductById(it)
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
