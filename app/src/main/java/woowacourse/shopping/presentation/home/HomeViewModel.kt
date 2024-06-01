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

    private val _uiState: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState())
    val uiState: LiveData<HomeUiState>
        get() = _uiState

    private val _uiEvent: MutableLiveData<Event<HomeUiEvent>> = MutableLiveData()
    val uiEvent: LiveData<Event<HomeUiEvent>>
        get() = _uiEvent

    init {
        loadProducts()
        loadHistory()
    }

    fun navigateToCart() {
        _uiEvent.value = Event(HomeUiEvent.NavigateToCart)
    }

    private fun loadProducts() {
        val currentPageData =
            nextPageProducts.ifEmpty {
                productRepository.fetchSinglePage(page)
            }
        nextPageProducts = productRepository.fetchSinglePage(++page)
        val isLoadingAvailable = nextPageProducts.isNotEmpty()
        _uiState.value = uiState.value?.copy(
            loadStatus = LoadStatus(loadingAvailable = isLoadingAvailable, isLoadingPage = false),
            products = uiState.value?.products?.plus(currentPageData) ?: return
        )
        updateTotalCount()
    }

    override fun navigateToProductDetail(id: Long) {
        val lastlyViewedId = productRepository.fetchLatestHistory()?.product?.id
        loadHistory()
        _uiEvent.value = Event(HomeUiEvent.NavigateToDetail(id, lastlyViewedId))
    }

    override fun loadNextPage() {
        loadProducts()
    }

    override fun onNavigatedBack(changedIds: LongArray) {
        if (changedIds.isEmpty()) return
        changedIds.forEach { id ->
            val updatedProduct = productRepository.fetchProduct(id)
            val target = getUpdatedProducts(id, updatedProduct.cartItem)
            _uiState.value = uiState.value?.copy(
                products = target ?: return@forEach
            )
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
        _uiState.value = uiState.value?.copy(
            products = target ?: return
        )
        updateTotalCount()
    }

    private fun getUpdatedProducts(
        targetProductId: Long,
        cartItemToUpdate: CartItem?
    ): List<CartableProduct>? {
        return uiState.value?.products?.map {
            if (it.product.id == targetProductId) {
                it.copy(cartItem = cartItemToUpdate)
            } else {
                it
            }
        }
    }

    private fun loadHistory() {
        _uiState.value = uiState.value?.copy(
            productHistory = productRepository.fetchProductHistory(10)
        )
    }

    private fun updateTotalCount() {
        _uiState.value = uiState.value?.copy(
            totalQuantity = cartRepository.fetchTotalCount()
        )
    }
}
