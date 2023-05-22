package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.products.uistate.ProductUIState
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
        view.addProducts(productRepository.findAll(limit, offset).map(ProductUIState::from))
    }

    override fun plusCount(productId: Long, oldCount: Int) {
        cartRepository.updateCount(productId, oldCount + 1)
/*        val isExistingItem: Boolean = cartRepository.findById(productId) != null
        val product: Product = productRepository.findById(productId) ?: return

        if (isExistingItem) {
            cartRepository.updateCount(productId, oldCount + 1)
        } else {
            cartRepository.save(
                CartProduct(
                    product.id,
                    product.imageUrl,
                    product.name,
                    product.price,
                    oldCount,
                ),
            )
        }*/
        view.updateCartItem(productId, oldCount + 1)
    }

    override fun minusCount(productId: Long, oldCount: Int) {
        if (oldCount <= MINIMUM_COUNT) {
            cartRepository.deleteById(productId)
            view.deleteCartItem(productId)
        } else {
            cartRepository.updateCount(productId, oldCount - 1)
            view.updateCartItem(productId, oldCount - 1)
        }
        /*val isExistingItem: Boolean = cartRepository.findById(productId) != null
        val product: Product = productRepository.findById(productId) ?: return

        if (isExistingItem) {
            cartRepository.updateCount(productId, oldCount - 1)
        } *//*else {
            cartRepository.save(
                CartProduct(
                    product.id,
                    product.imageUrl,
                    product.name,
                    product.price,
                    oldCount,
                ),
            )
        }*/
    }

    override fun startCount(product: ProductUIState) {
        product.updateCount(MINIMUM_COUNT)
        cartRepository.save(
            CartProduct(product.id, product.imageUrl, product.name, product.price, product.count),
        )
        view.updateCartItem(product.id, product.count)
    }

    companion object {
        private const val MAX_SIZE_RECENTLY_VIEWED_PRODUCTS = 10
        private const val MINIMUM_COUNT = 1
    }
}
