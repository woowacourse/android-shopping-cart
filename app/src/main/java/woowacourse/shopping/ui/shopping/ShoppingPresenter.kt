package woowacourse.shopping.ui.shopping

import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentRepository

class ShoppingPresenter(
    private val view: ShoppingContract.View,
    private val productRepository: ProductRepository,
    private val recentRepository: RecentRepository,
    private val cartRepository: CartRepository
) : ShoppingContract.Presenter {
    private var cartProductsData: List<CartProductUIModel> = cartRepository.getAll().toUIModel()

    override fun setUpProducts() {
        val recentProductsData = recentRepository.getRecent(RECENT_PRODUCT_COUNT)
            .map { it.toUIModel() }
        val productsData = productRepository.getNext(PRODUCT_COUNT).map { it.toUIModel() }
        val cartProductsData = cartRepository.getAll().toUIModel()

        view.setProducts(productsData, recentProductsData, cartProductsData)
    }

    override fun addMoreProducts() {
        val productsData = productRepository.getNext(PRODUCT_COUNT).map { it.toUIModel() }

        view.addMoreProducts(productsData)
    }

    override fun updateProducts() {
        val recentProductsData = recentRepository.getRecent(RECENT_PRODUCT_COUNT)
            .map { it.toUIModel() }
        val cartProductsData = cartRepository.getAll().toUIModel()

        view.refreshProducts(recentProductsData, cartProductsData)
        updateToolbar()
    }

    override fun updateItem(productId: Int, count: Int): Int {
        cartRepository.insert(productId)
        val updatedCount = cartRepository.updateCount(productId, count)
        cartProductsData = cartRepository.getAll().toUIModel()
        updateToolbar()
        return updatedCount
    }

    override fun updateToolbar() {
        view.updateToolbar(cartRepository.getTotalSelectedCount())
    }

    override fun navigateToItemDetail(productId: Int) {
        productRepository.findById(productId).let {
            view.navigateToProductDetail(it.toUIModel())
        }
    }

    companion object {
        private const val RECENT_PRODUCT_COUNT = 10
        private const val PRODUCT_COUNT = 20
    }
}
