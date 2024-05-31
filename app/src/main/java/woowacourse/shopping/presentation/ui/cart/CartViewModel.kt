package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.state.UIState

class CartViewModel(private val repository: CartRepository) : ViewModel(), CartActionHandler {
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

    private val _isPageControlVisible = MutableLiveData<Boolean>()
    val isPageControlVisible: LiveData<Boolean> = _isPageControlVisible

    private val _navigateToProductDetail = MutableLiveData<Long>()
    val navigateToProductDetail: LiveData<Long> = _navigateToProductDetail

    private val _navigateToShopping = MutableLiveData<MutableSet<Long>>()
    val navigateToShopping: LiveData<MutableSet<Long>> = _navigateToShopping

    private val modifiedProductIds = mutableSetOf<Long>()

    private var lastPage: Int = DEFAULT_PAGE

    val cartItemsState: LiveData<UIState<List<CartItem>>> =
        currentPage.switchMap { page ->
            MutableLiveData<UIState<List<CartItem>>>().apply {
                repository.findCartItemsByPage(page, pageSize)
                    .onSuccess { items ->
                        value =
                            if (items.items.isEmpty()) {
                                UIState.Empty
                            } else {
                                UIState.Success(items.items)
                            }
                    }
                    .onFailure { exception ->
                        value = UIState.Error(exception)
                    }
            }
        }

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

        updatePageControlVisibility(totalItems)
    }

    fun loadNextPage() {
        val nextPage = (_currentPage.value ?: DEFAULT_PAGE) + PAGE_STEP
        loadPage(nextPage)
    }

    fun loadPreviousPage() {
        val prevPage = (_currentPage.value ?: DEFAULT_PAGE) - PAGE_STEP
        loadPage(prevPage)
    }

    fun navigateToShopping() {
        _navigateToShopping.postValue(modifiedProductIds)
    }

    override fun onProductClick(productId: Long) {
        _navigateToProductDetail.postValue(productId)
    }

    override fun onDeleteItemClick(productId: Long) {
        repository.deleteByProductId(productId)
        modifiedProductIds.add(productId)
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    override fun onPlusButtonClicked(productId: Long) {
        repository.plusQuantity(productId, 1)
        modifiedProductIds.add(productId)
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    override fun onMinusButtonClicked(productId: Long) {
        repository.minusQuantity(productId, 1)
        modifiedProductIds.add(productId)
        loadPage(_currentPage.value ?: DEFAULT_PAGE)
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_PAGE = 0
        private const val PAGE_STEP = 1
    }
}
