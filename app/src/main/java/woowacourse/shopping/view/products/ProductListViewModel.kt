package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentViewedItemRepository
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.ProductUpdate

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentViewedItemRepository: RecentViewedItemRepository,
) : ViewModel(), ProductListActionHandler, CountActionHandler {
    private val _products: MutableLiveData<PagingResult> =
        MutableLiveData(PagingResult(emptyList(), false))
    val products: LiveData<PagingResult> get() = _products

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>> get() = _navigateToCart

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>> get() = _navigateToDetail

    private val _updateProductCount = MutableLiveData<ProductUpdate>()

    val updateProductCount: LiveData<ProductUpdate> = _updateProductCount

    private val _totalCount = MutableLiveData(0)
    val totalCount: LiveData<Int> get() = _totalCount

    private val _recentViewed: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val recentViewed: LiveData<List<Product>> = _recentViewed

    init {
        loadPagingProductData()
        updateTotalCount()
        updateRecentViewedItems()
    }

    private fun loadPagingProductData() {
        val loadedItems = products.value?.items ?: emptyList()
        val pagingProducts =
            productRepository.loadPagingProducts(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)
        val hasNextPage =
            productRepository.hasNextProductPage(loadedItems.size, PRODUCT_LOAD_PAGING_SIZE)

        val cartItems = cartRepository.loadAllCartItems()
        val cartMap = cartItems.associateBy { it.product.id }

        val newProductsWithQuantity =
            pagingProducts.map { product ->
                val quantity = cartMap[product.id]?.quantity ?: DEFAULT_QUANTITY
                ProductWithQuantity(product, quantity)
            }

        val updatedProductList = loadedItems + newProductsWithQuantity
        _products.value = PagingResult(updatedProductList, hasNextPage)
    }

    override fun onProductItemClicked(product: Product) {
        _navigateToDetail.value = Event(product.id)
    }

    override fun onShoppingCartButtonClicked() {
        _navigateToCart.value = Event(true)
    }

    override fun onMoreButtonClicked() {
        loadPagingProductData()
    }

    override fun onIncreaseQuantityButtonClicked(product: Product) {
        val updatedQuantity = cartRepository.updateIncrementQuantity(product, INCREMENT_VALUE)
        _updateProductCount.value = ProductUpdate(product.id, updatedQuantity)
        _totalCount.value = _totalCount.value?.plus(INCREMENT_VALUE)
    }

    override fun onDecreaseQuantityButtonClicked(product: Product) {
        val updatedQuantity = cartRepository.updateDecrementQuantity(product, DECREMENT_VALUE, true)
        _updateProductCount.value = ProductUpdate(product.id, updatedQuantity)
        _totalCount.value = _totalCount.value?.minus(DECREMENT_VALUE)
    }

    fun updateTotalCount() {
        _totalCount.value = cartRepository.getTotalNumberOfCartItems()
    }

    fun updateRecentViewedItems() {
        _recentViewed.value = recentViewedItemRepository.loadAllRecentViewedItems(MAXIMUM_RECENT_VIEWED_ITEMS_COUNT)
    }

    companion object {
        const val PRODUCT_LOAD_PAGING_SIZE = 20
        const val DEFAULT_QUANTITY = 0
        const val INCREMENT_VALUE = 1
        const val DECREMENT_VALUE = 1
        const val MAXIMUM_RECENT_VIEWED_ITEMS_COUNT = 10
    }
}
