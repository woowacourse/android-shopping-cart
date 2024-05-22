package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.state.UIState

class CartViewModel(private val repository: CartRepository) : ViewModel(), CartItemClickListener {
    private var lastPage: Int = DEFAULT_PAGE

    private val _cartUiState = MutableLiveData<UIState<List<CartItem>>>(UIState.Empty)
    val cartUiState: LiveData<UIState<List<CartItem>>>
        get() = _cartUiState

    private val _currentPage = MutableLiveData(DEFAULT_PAGE)
    val currentPage: LiveData<Int> = _currentPage

    private val _isFirstPage = MutableLiveData(true)
    val isFirstPage: LiveData<Boolean> = _isFirstPage

    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _isEmpty = MutableLiveData(true)
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isPageControlVisible = MutableLiveData(false)
    val isPageControlVisible: LiveData<Boolean> = _isPageControlVisible

    private val _navigateToDetail = MutableLiveData<Event<Long>>()
    val navigateToDetail: LiveData<Event<Long>>
        get() = _navigateToDetail

    private val _notifyDeletion = MutableLiveData<Event<Boolean>>()
    val notifyDeletion: LiveData<Event<Boolean>>
        get() = _notifyDeletion

    private val _isBackButtonClicked = MutableLiveData<Event<Boolean>>()
    val isBackButtonClicked: LiveData<Event<Boolean>>
        get() = _isBackButtonClicked

    init {
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    private fun updatePageControlVisibility(totalItems: Int) {
        _isPageControlVisible.value = totalItems > PAGE_SIZE
    }

    fun loadPage(page: Int) {
        val totalItems = repository.size()
        lastPage = (totalItems - PAGE_STEP) / PAGE_SIZE

        _currentPage.value = page.coerceIn(DEFAULT_PAGE, lastPage)
        _isFirstPage.value = _currentPage.value == DEFAULT_PAGE
        _isLastPage.value = _currentPage.value == lastPage

        loadCartItems()
        updatePageControlVisibility(totalItems)
    }

    fun loadCartItems() {
        try {
            val cartItems =
                repository.findAllPagedItems(currentPage.value ?: DEFAULT_PAGE, PAGE_SIZE)
            if (cartItems.isEmpty()) {
                _cartUiState.value = UIState.Empty
                _isEmpty.value = true
            } else {
                _cartUiState.value = UIState.Success(cartItems)
                _isEmpty.value = false
            }
        } catch (e: Exception) {
            _cartUiState.value = UIState.Error(e)
        }
    }

    fun loadNextPage() {
        val nextPage = (currentPage.value ?: DEFAULT_PAGE) + PAGE_STEP
        loadPage(nextPage)
    }

    fun loadPreviousPage() {
        val prevPage = (currentPage.value ?: DEFAULT_PAGE) - PAGE_STEP
        loadPage(prevPage)
    }

    fun deleteItem(itemId: Long) {
        repository.delete(itemId)
        loadPage(currentPage.value ?: DEFAULT_PAGE)
    }

    companion object {
        const val PAGE_SIZE = 5
        private const val DEFAULT_PAGE = 0
        private const val PAGE_STEP = 1
    }

    override fun onCartItemClick(productId: Long) {
        _navigateToDetail.value = Event(productId)
    }

    override fun onDeleteButtonClick(itemId: Long) {
        deleteItem(itemId)
        _notifyDeletion.value = Event(true)
    }

    override fun onBackButtonClick() {
        _isBackButtonClicked.value = Event(true)
    }
}
