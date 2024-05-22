package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.state.UIState

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val pageSize = PAGE_SIZE

    private val _currentPage = MutableLiveData(DEFAULT_PAGE)
    val currentPage: LiveData<Int> = _currentPage

    val isFirstPage: LiveData<Boolean> =
        currentPage.map { page ->
            page == DEFAULT_PAGE
        }

    val isLastPage: LiveData<Boolean> =
        currentPage.map { page ->
            page == lastPage
        }
    private val _totalItemSize = MutableLiveData<Int>(repository.size())
    val totalItemSize: LiveData<Int> = _totalItemSize

    private val _isPageControlVisible = MutableLiveData<Boolean>(((totalItemSize.value ?: 0) > PAGE_SIZE))
    val isPageControlVisible: LiveData<Boolean> = _isPageControlVisible

    private var lastPage: Int = DEFAULT_PAGE

    val cartItemsState: LiveData<UIState<List<CartItem>>> =
        currentPage.switchMap { page ->
            MutableLiveData<UIState<List<CartItem>>>().apply {
                value =
                    try {
                        setUpUIState(page)
                    } catch (e: Exception) {
                        UIState.Error(e)
                        setUpUIState(page)
                    }
            }
        }

    private fun setUpUIState(page: @JvmSuppressWildcards Int): UIState<List<CartItem>> {
        val items = repository.findAllPagedItems(page, pageSize).items
        return if (items.isEmpty()) {
            UIState.Empty
        } else {
            UIState.Success(items)
        }
    }

    init {
        loadPage()
    }

    private fun updatePageControlVisibility() {
        _totalItemSize.postValue(repository.size())
        lastPage = ((totalItemSize.value ?: 0) - PAGE_STEP) / pageSize
        _isPageControlVisible.postValue((totalItemSize.value ?: 0) > pageSize)
    }

    private fun loadPage() {
        _currentPage.value = currentPage.value?.coerceIn(DEFAULT_PAGE, lastPage)
        updatePageControlVisibility()
    }

    fun loadNextPage() {
        val nextPage = (currentPage.value ?: DEFAULT_PAGE) + PAGE_STEP
        _currentPage.value = nextPage.coerceIn(DEFAULT_PAGE, lastPage)
        updatePageControlVisibility()
    }

    fun loadPreviousPage() {
        val prevPage = (currentPage.value ?: DEFAULT_PAGE) - PAGE_STEP
        _currentPage.value = prevPage.coerceIn(DEFAULT_PAGE, lastPage)
        updatePageControlVisibility()
    }

    fun deleteItem(itemId: Long) {
        repository.delete(itemId)
        loadPage()
    }

    fun isCartEmpty(): Boolean = cartItemsState.value == UIState.Empty

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_PAGE = 0
        private const val PAGE_STEP = 1
    }
}
