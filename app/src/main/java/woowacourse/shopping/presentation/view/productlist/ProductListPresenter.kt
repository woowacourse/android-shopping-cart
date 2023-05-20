package woowacourse.shopping.presentation.view.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.product.ProductRepository
import woowacourse.shopping.data.respository.recentproduct.RecentProductRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProductListPresenter(
    private val view: ProductContract.View,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val cartRepository: CartRepository
) : ProductContract.Presenter {
    private val products = mutableListOf<ProductModel>()
    private val recentProducts = mutableListOf<RecentProductModel>()
    private val _cartCount = MutableLiveData<Int>()
    override val cartCount: LiveData<Int>
        get() = _cartCount

    override fun deleteNotTodayRecentProducts() {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN))
        recentProductRepository.deleteNotTodayRecentProducts(today)
    }

    override fun loadProductItems() {
        products.addAll(productRepository.getData(0, LOAD_PRODUCT_COUNT))
        view.setProductItemsView(products.toList())
        updateCartCount()
    }

    override fun loadRecentProductItems() {
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        view.setRecentProductItemsView(recentProducts.toList())
    }

    override fun updateRecentProductItems() {
        recentProducts.clear()
        recentProducts.addAll(recentProductRepository.getRecentProducts())
        view.updateRecentProductItemsView()
    }

    override fun saveRecentProduct(productId: Long) {
        recentProductRepository.addCart(productId)
    }

    override fun loadMoreData(startPosition: Int) {
        val newProducts = productRepository.getData(startPosition, LOAD_PRODUCT_COUNT)
        products.addAll(newProducts)
        view.updateMoreProductsView(newProducts)
    }

    override fun updateCartProduct(productId: Long, count: Int, position: Int) {
        cartRepository.insertCart(productId, count)
        products[position] = products[position].copy(count = count)
        updateCartCount()
    }

    private fun updateCartCount() {
        _cartCount.value = products.sumOf {
            it.count
        }
    }

    companion object {
        private const val LOCAL_DATE_PATTERN = "yyyy-MM-dd"
        private const val LOAD_PRODUCT_COUNT = 20
    }
}
