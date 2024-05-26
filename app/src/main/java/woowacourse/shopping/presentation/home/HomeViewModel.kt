package woowacourse.shopping.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.history.ProductHistoryRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.home.products.HomeItemEventListener
import woowacourse.shopping.presentation.home.products.QuantityListener
import woowacourse.shopping.presentation.util.Event
import kotlin.concurrent.thread

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productHistoryRepository: ProductHistoryRepository,
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

    private val _changedPosition: MutableLiveData<Event<Int>> = MutableLiveData()
    val changedPosition: LiveData<Event<Int>>
        get() = _changedPosition

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
        thread {
            _loadStatus.postValue(
                loadStatus.value?.copy(
                    isLoadingPage = true,
                    loadingAvailable = false,
                ),
            )
            val currentPageData =
                nextPageProducts.ifEmpty {
                    productRepository.fetchSinglePage(page)
                }
            nextPageProducts = productRepository.fetchSinglePage(++page)
            _products.postValue(products.value?.plus(currentPageData))
            val isLoadingAvailable = nextPageProducts.isNotEmpty()
            _loadStatus.postValue(
                loadStatus.value?.copy(
                    loadingAvailable = isLoadingAvailable,
                    isLoadingPage = false,
                ),
            )
            _totalQuantity.postValue(
                cartRepository.fetchTotalCount(),
            )
        }
    }

    fun loadHistory() {
        thread {
            _productHistory.postValue(
                productHistoryRepository.fetchProductHistory(10),
            )
        }
    }

    fun navigateToCart() {
        _navigateToCartEvent.value = Event(Unit)
    }

    override fun navigateToProductDetail(id: Long) {
        thread {
            val lastlyViewedId = productHistoryRepository.fetchLatestHistory()?.product?.id
            loadHistory()
            _navigateToDetailEvent.postValue(Event(DetailNavigationData(id, lastlyViewedId)))
        }
    }

    override fun loadNextPage() {
        loadProducts()
    }

    override fun onQuantityChange(
        productId: Long,
        quantity: Int,
    ) {
        if (quantity < 0) return
        thread {
            val targetProduct = productRepository.fetchProduct(productId)
            if (quantity == 0) {
                if (targetProduct.cartItem != null) {
                    cartRepository.removeCartItem(targetProduct.cartItem)
                }
                val target =
                    products.value?.map {
                        if (it.product.id == targetProduct.product.id) {
                            val item = it.copy(cartItem = null)
                            item
                        } else {
                            it
                        }
                    }
                _products.postValue(target)
                _changedPosition.postValue(
                    Event(
                        products.value?.indexOfFirst { it.product.id == targetProduct.product.id }
                            ?: return@thread,
                    ),
                )
            } else {
                if (targetProduct.cartItem?.id != null) {
                    cartRepository.updateQuantity(targetProduct.cartItem.id, quantity)
                    targetProduct.cartItem.id
                } else {
                    cartRepository.addCartItem(CartItem(productId = productId, quantity = quantity))
                }
                val target =
                    products.value?.map {
                        if (it.product.id == productId) {
                            val item =
                                it.copy(cartItem = productRepository.fetchProduct(productId).cartItem)
                            item
                        } else {
                            it
                        }
                    }
                _products.postValue(target)
                _changedPosition.postValue(
                    Event(
                        products.value?.indexOfFirst { it.product.id == productId }
                            ?: return@thread,
                    ),
                )
            }
            _totalQuantity.postValue(
                cartRepository.fetchTotalCount(),
            )
        }
    }
}
