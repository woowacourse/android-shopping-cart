package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository
import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState
import java.time.LocalDateTime

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository
) : ProductListContract.Presenter {

    private var currentPage = 0
    private val offset
        get() = (currentPage - 1) * PAGE_SIZE

    override fun getCurrentPage(): Int {
        return currentPage
    }

    override fun restoreCurrentPage(currentPage: Int) {
        this.currentPage = currentPage
        val products = productRepository.findAll(offset, PAGE_SIZE)
        val productUIStates = products.map(ProductUIState::from)
        view.addProducts(productUIStates)
        refreshCanLoadMore()
    }

    override fun onLoadRecentlyViewedProducts() {
        val recentlyViewedProducts =
            recentlyViewedProductRepository.findFirst10OrderByViewedTimeDesc()

        val recentlyViewedProductUIStates =
            recentlyViewedProducts.map(RecentlyViewedProductUIState::from)
        view.setRecentlyViewedProducts(recentlyViewedProductUIStates)
    }

    override fun onLoadProductsNextPage() {
        currentPage++
        val products = productRepository.findAll(PAGE_SIZE, offset)
        val productUIStates = products.map {
            val cartItem = cartItemRepository.findByProductId(it.id)
            if (cartItem != null) {
                ProductUIState.from(cartItem)
            } else {
                ProductUIState.from(it)
            }
        }
        view.addProducts(productUIStates)
        refreshCanLoadMore()
    }

    override fun onRefreshProducts() {
        val products = productRepository.findAll(offset + PAGE_SIZE, 0)
        val productUIStates = products.map {
            val cartItem = cartItemRepository.findByProductId(it.id)
            if (cartItem != null) {
                ProductUIState.from(cartItem)
            } else {
                ProductUIState.from(it)
            }
        }
        view.setProducts(productUIStates)
    }

    private fun refreshCanLoadMore() {
        val maxPage = (productRepository.countAll() - 1) / PAGE_SIZE + 1
        if (currentPage >= maxPage) view.setCanLoadMore(false)
    }

    override fun onAddToCart(productId: Long) {
        val product = productRepository.findById(productId) ?: return
        val cartItem = CartItem(product, LocalDateTime.now(), 1)
        cartItemRepository.save(cartItem)
        view.replaceProduct(ProductUIState.from(cartItem))
        onLoadCartItemCount()
    }

    override fun onPlusCount(cartItemId: Long) {
        val cartItem = cartItemRepository.findById(cartItemId) ?: return
        cartItem.plusCount()
        cartItemRepository.updateCountById(cartItemId, cartItem.count)
        view.replaceProduct(ProductUIState.from(cartItem))
    }

    override fun onMinusCount(cartItemId: Long) {
        val cartItem = cartItemRepository.findById(cartItemId) ?: return
        if (cartItem.count == 1) {
            cartItemRepository.deleteById(cartItemId)
            view.replaceProduct(ProductUIState.from(cartItem.product))
            onLoadCartItemCount()
            return
        }
        cartItem.minusCount()
        cartItemRepository.updateCountById(cartItemId, cartItem.count)
        view.replaceProduct(ProductUIState.from(cartItem))
    }

    override fun onLoadCartItemCount() {
        val count = cartItemRepository.countAll()
        view.setCartItemCount(count)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
