package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.state.UIState

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val pageSize = PAGE_SIZE

    private val _cartUIState = MutableLiveData<UIState<List<CartItem>>>()
    val cartUIState: LiveData<UIState<List<CartItem>>> = _cartUIState

    private val _currentPage = MutableLiveData(DEFAULT_PAGE)
    val currentPage: LiveData<Int> = _currentPage

    private val _isFirstPage = MutableLiveData(true)
    val isFirstPage: LiveData<Boolean> = _isFirstPage

    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _isPageControlVisible = MutableLiveData<Boolean>()
    val isPageControlVisible: LiveData<Boolean> = _isPageControlVisible

    private var lastPage: Int = DEFAULT_PAGE

    init {
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    private fun updatePageControlVisibility(totalItems: Int) {
        _isPageControlVisible.postValue(totalItems > pageSize)
    }

    fun loadPage(page: Int) {
        val totalItems = repository.size()
        lastPage = (totalItems - PAGE_STEP) / pageSize

        _currentPage.value = page.coerceIn(DEFAULT_PAGE, lastPage)
        _isFirstPage.value = _currentPage.value == DEFAULT_PAGE
        _isLastPage.value = _currentPage.value == lastPage

        loadCartItems()
        updatePageControlVisibility(totalItems)
    }

    fun loadCartItems() {
        try {
            // throw IllegalArgumentException("error")
            val items =
                repository.findAllPagedItems(_currentPage.value ?: DEFAULT_PAGE, pageSize).items
            if (items.isEmpty()) {
                _cartUIState.postValue(UIState.Empty)
            } else {
                _cartUIState.postValue(UIState.Success(items))
            }
        } catch (e: Exception) {
            _cartUIState.postValue(UIState.Error(e))
        }
    }

    fun showEmptyView() {
        _cartUIState.postValue(UIState.Empty)
    }

    fun loadNextPage() {
        val nextPage = (_currentPage.value ?: DEFAULT_PAGE) + PAGE_STEP
        loadPage(nextPage)
    }

    fun loadPreviousPage() {
        val prevPage = (_currentPage.value ?: DEFAULT_PAGE) - PAGE_STEP
        loadPage(prevPage)
    }

    fun deleteItem(itemId: Long) {
        repository.delete(itemId)
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_PAGE = 0
        private const val PAGE_STEP = 1
    }
}
