package woowacourse.shopping.ui.shopping

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository
) : ShoppingContract.Presenter {
    override fun setUpProducts() {
        fetchCartProducts()
        fetchNextProducts()
        fetchRecentProducts()
    }

    override fun fetchNextProducts() {
        view.addMoreProducts(
            productRepository.getNext(PRODUCT_COUNT).map { it.toUIModel() }
        )
    }

    override fun fetchRecentProducts() {
        view.setRecentProducts(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() }
        )
    }

    override fun fetchCartProducts() {
        view.setCartProducts(
            cartRepository.getAll().toUIModel()
        )
    }

    override fun updateItemCount(productId: Int, count: Int): Int {
        cartRepository.insert(
            productRepository.findById(productId)
        )
        return cartRepository.updateCount(productId, count)
    }

    override fun fetchTotalCount() {
        view.updateToolbar(cartRepository.getTotalSelectedCount())
    }

    override fun navigateToItemDetail(productId: Int) {
        view.navigateToProductDetail(
            productRepository.findById(productId).toUIModel()
        )
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
