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
        setUpCartCounts()
        setUpNextProducts()
        setUpRecentProducts()
    }

    override fun setUpCartCounts() {
        cartRepository.getAll().toUIModel()
            .associateBy { it.id }
            .mapValues { it.value.count }
            .let { view.setCartProducts(it) }
    }

    override fun setUpNextProducts() {
        view.addMoreProducts(
            productRepository.getNext(PRODUCT_COUNT).map { it.toUIModel() }
        )
    }

    override fun setUpRecentProducts() {
        view.setRecentProducts(
            recentRepository.getRecent(RECENT_PRODUCT_COUNT).map { it.toUIModel() }
        )
    }

    override fun setUpTotalCount() {
        view.setToolbar(cartRepository.getTotalSelectedCount())
    }

    override fun updateItemCount(productId: Int, count: Int): Int {
        cartRepository.insert(
            productRepository.findById(productId)
        )
        return cartRepository.updateCount(productId, count)
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
