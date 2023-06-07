package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.cart.uistate.CartUIState
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.ProductUIState.Companion.MINIMUM_COUNT
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ProductListContract.Presenter {

    override fun loadRecentlyViewedProducts() {
        view.setRecentlyViewedProducts(
            recentlyViewedProductRepository.findAll().map(RecentlyViewedProductUIState::from)
                .reversed().take(MAX_SIZE_RECENTLY_VIEWED_PRODUCTS),
        )
    }

    override fun loadProducts(limit: Int, offset: Int) {
        val cartProducts = cartRepository.findAll().map(CartUIState::from)
        val products = productRepository.findAll(limit, offset).map(ProductUIState::from)
        products.forEach { product ->
            cartProducts.find { it.id == product.id }?.let {
                product.updateCount(it.count)
            }
        }
        view.addProducts(products)
    }

    override fun loadProductsCartCount() {
        val cartProducts = cartRepository.findAll()
        view.updateProductsCartCount(cartProducts.map(CartUIState::from))
    }

    override fun loadCartItemCount() {
        val cartSize = cartRepository.findAll()
            .fold(0) { count, cartProduct -> count + cartProduct.count }
        view.updateCartItemCount(cartSize >= MINIMUM_COUNT, cartSize)
    }

    override fun plusCount(productId: Long, oldCount: Int) {
        cartRepository.updateCount(productId, oldCount + 1)
        view.updateCartItem(productId, oldCount + 1)
        loadCartItemCount()
    }

    override fun minusCount(productId: Long, oldCount: Int) {
        if (oldCount <= MINIMUM_COUNT) {
            cartRepository.deleteById(productId)
            view.deleteCartItem(productId)
        } else {
            cartRepository.updateCount(productId, oldCount - 1)
            view.updateCartItem(productId, oldCount - 1)
        }
        loadCartItemCount()
    }

    override fun startCount(product: ProductUIState) {
        product.updateCount(MINIMUM_COUNT)
        cartRepository.save(
            CartProduct(product.id, product.imageUrl, product.name, product.price, product.count),
        )
        view.updateCartItem(product.id, product.count)
        loadCartItemCount()
    }

    override fun navigateToCart() {
        view.moveToCartActivity()
    }

    override fun navigateToProductDetail(productId: Long) {
        view.moveToProductDetailActivity(productId)
    }

    companion object {
        private const val MAX_SIZE_RECENTLY_VIEWED_PRODUCTS = 10
    }
}
