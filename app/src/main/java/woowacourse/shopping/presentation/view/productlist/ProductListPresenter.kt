package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.product.ProductRepositoryImpl
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository
) : ProductContract.Presenter {
    private val products = mutableListOf<ProductModel>()
    private val recentProducts = mutableListOf<RecentProductModel>()
    private var lastScroll = 0

    override fun initRecentProductItems() {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN))
        recentProductRepository.deleteNotTodayRecentProducts(today)
    }

    override fun loadProductItems() {
        products.addAll(
            productRepository.getData(LOAD_PRODUCT_START_POSITION, LOAD_PRODUCT_COUNT)
                .map { it.toUIModel() }
        )
        view.setProductItemsView(products)
    }

    override fun loadRecentProductItems() {
        recentProducts.addAll(
            recentProductRepository.getRecentProducts(LOAD_RECENT_PRODUCT_COUNT)
                .filter { it.id != UNABLE_ID }
                .map { it.toUIModel() }
        )
        view.setRecentProductItemsView(recentProducts)
    }

    override fun loadCartItems() {
        val carts = cartRepository.getAllCarts()
        products.forEach { product ->
            product.count = carts.find { it.productId == product.id }?.count ?: 0
        }
        val allCount = carts.sumOf { it.count }
        view.updateToolbarCartCountView(allCount)
        updateVisibilityCartCount(allCount)
    }

    override fun updateRecentProductItems() {
        recentProducts.clear()
        recentProducts.addAll(
            recentProductRepository.getRecentProducts(LOAD_RECENT_PRODUCT_COUNT)
                .filter { it.id != UNABLE_ID }
                .map { it.toUIModel() }
        )
        view.updateRecentProductItemsView(0, recentProducts.size)
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }

    override fun loadMoreData() {
        val startPosition = products.size
        val newProducts =
            productRepository.getData(startPosition, LOAD_PRODUCT_COUNT).map { it.toUIModel() }
        products.addAll(newProducts)
        view.updateMoreProductsView(startPosition, newProducts.size)
    }

    override fun actionOptionItem() {
        view.moveToCartView()
    }

    override fun getLastRecentProductItem(lastRecentIndex: Int): RecentProductModel {
        val lastRecentProducts = recentProductRepository.getRecentProducts(LAST_RECENT_COUNT)
        return lastRecentProducts[lastRecentIndex].toUIModel()
    }

    override fun getRecentProductsLastScroll(): Int = lastScroll

    override fun updateRecentProductsLastScroll(lastScroll: Int) {
        this.lastScroll = lastScroll
    }

    override fun updateCount(productId: Long, count: Int) {
        updateProductCount(productId, count)
        updateCartCount()
    }

    private fun updateProductCount(productId: Long, count: Int) {
        val product = products.find { it.id == productId } ?: return
        product.count = count
        if (count == 0) {
            cartRepository.deleteCartByProductId(productId)
            return
        }
        cartRepository.updateCartByProductId(productId, count, 1)
    }

    private fun updateCartCount() {
        val carts = cartRepository.getAllCarts()
        val allCount = carts.sumOf { it.count }
        view.updateToolbarCartCountView(allCount)
        updateVisibilityCartCount(allCount)
    }

    private fun updateVisibilityCartCount(count: Int) {
        if (count == 0) {
            view.setGoneToolbarCartCountView()
            return
        }
        view.setVisibleToolbarCartCountView()
    }

    companion object {
        private const val LOAD_PRODUCT_START_POSITION = 0
        private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
        private const val LOAD_RECENT_PRODUCT_COUNT = 10
        private const val LOAD_PRODUCT_COUNT = 20

        private const val LAST_RECENT_COUNT = 2

        private const val UNABLE_ID = -1L
    }
}
