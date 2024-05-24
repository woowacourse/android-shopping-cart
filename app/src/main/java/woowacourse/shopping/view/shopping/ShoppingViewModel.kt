package woowacourse.shopping.view.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.state.UIState

class ShoppingViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
) :
    ViewModel(),
        ShoppingClickListener {
    private val _shoppingUiState = MutableLiveData<UIState<List<Product>>>(UIState.Empty)
    val shoppingUiState: LiveData<UIState<List<Product>>>
        get() = _shoppingUiState

    private val _canLoadMore = MutableLiveData(false)
    val canLoadMore: LiveData<Boolean>
        get() = _canLoadMore

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int>
        get() = _totalQuantity

    private val _isQuantityControlVisible = MutableLiveData(false)
    val isQuantityControlVisible: LiveData<Boolean>
        get() = _isQuantityControlVisible

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val loadedProducts: MutableList<Product> = mutableListOf()

    init {
        loadProducts()
        loadTotalQuantity()
    }

    fun loadProducts() {
        try {
            val products = shoppingRepository.findProductsByPage()
            if (products.isEmpty()) {
                _shoppingUiState.value = UIState.Empty
            } else {
                _canLoadMore.value = shoppingRepository.canLoadMore()
                _shoppingUiState.value = UIState.Success(loadedProducts + products)
                loadedProducts += products
            }
        } catch (e: Exception) {
            _shoppingUiState.value = UIState.Error(e)
        }
    }

    private fun loadTotalQuantity() {
        _totalQuantity.value = cartRepository.totalQuantity()
    }

    override fun onLoadMoreButtonClick() {
        loadProducts()
    }

    override fun onShoppingCartButtonClick() {
        _navigateToCart.value = Event(true)
    }

    override fun onPlusButtonClick(product: Product) {
        cartRepository.insert(product)
        _isQuantityControlVisible.value = true
        loadTotalQuantity()
    }

    override fun onProductClick(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
