package woowacourse.shopping.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.home.products.HomeItemEventListener
import woowacourse.shopping.presentation.home.products.QuantityListener
import woowacourse.shopping.presentation.util.Event

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), HomeItemEventListener, QuantityListener {
    private var page: Int = 0
    private var nextPageProducts: List<CartableProduct> = emptyList()

    private val _products: MutableLiveData<List<CartableProduct>> =
        MutableLiveData<List<CartableProduct>>(emptyList())
    val products: LiveData<List<CartableProduct>>
        get() = _products

    private val _loadStatus: MutableLiveData<LoadStatus> = MutableLiveData(LoadStatus())
    val loadStatus: LiveData<LoadStatus>
        get() = _loadStatus

    private val _navigateToDetailEvent: MutableLiveData<Event<DetailNavigationData>> =
        MutableLiveData()
    val navigateToDetailEvent: LiveData<Event<DetailNavigationData>>
        get() = _navigateToDetailEvent

    private val _navigateToCartEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigateToCartEvent: LiveData<Event<Unit>>
        get() = _navigateToCartEvent

    private val _totalQuantity: MutableLiveData<Int> = MutableLiveData(0)
    val totalQuantity: LiveData<Int>
        get() = _totalQuantity

    private val _productHistory: MutableLiveData<List<RecentProduct>> =
        MutableLiveData(emptyList())
    val productHistory: LiveData<List<RecentProduct>>
        get() = _productHistory

    init {
        loadProducts()
        loadHistory()
    }

    private fun loadProducts() {
        _loadStatus.value = loadStatus.value?.copy(isLoadingPage = true, loadingAvailable = false)
        val currentPageData =
            nextPageProducts.ifEmpty {
                productRepository.fetchSinglePage(page)
            }
        nextPageProducts = productRepository.fetchSinglePage(++page)
        _products.value = products.value?.plus(currentPageData)
        val isLoadingAvailable = nextPageProducts.isNotEmpty()

        _loadStatus.value =
            loadStatus.value?.copy(loadingAvailable = isLoadingAvailable, isLoadingPage = false)
        updateTotalCount()
    }

    fun loadHistory() {
        _productHistory.value = productRepository.fetchProductHistory(10)
    }

    fun navigateToCart() {
        _navigateToCartEvent.value = Event(Unit)
    }

    override fun navigateToProductDetail(id: Long) {
        val lastlyViewedId = productRepository.fetchLatestHistory()?.product?.id
        loadHistory()
        _navigateToDetailEvent.postValue(Event(DetailNavigationData(id, lastlyViewedId)))
    }

    override fun loadNextPage() {
        loadProducts()
    }

    override fun onNavigatedBack(changedIds: LongArray?) {
        if (changedIds == null) return
        changedIds.forEach { id ->
            val updatedProduct = productRepository.fetchProduct(id)
            val target = getUpdatedProducts(id, updatedProduct.cartItem)
            _products.value = target
        }
        loadHistory()
        updateTotalCount()
    }

    override fun onQuantityChange(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 0) return
        val targetProduct = productRepository.fetchProduct(productId)
        cartRepository.patchQuantity(productId, quantity, targetProduct.cartItem)
        val target = if (quantity == 0) {
            getUpdatedProducts(targetProduct.product.id, null)
        } else {
            getUpdatedProducts(productId, productRepository.fetchProduct(productId).cartItem)
        }
        _products.value = target
        updateTotalCount()
    }

    private fun getUpdatedProducts(
        targetProductId: Long,
        cartItemToUpdate: CartItem?
    ): List<CartableProduct>? {
        return products.value?.map {
            if (it.product.id == targetProductId) {
                it.copy(cartItem = cartItemToUpdate)
            } else {
                it
            }
        }
    }

    private fun updateTotalCount() {
        _totalQuantity.value = cartRepository.fetchTotalCount()
    }
}
