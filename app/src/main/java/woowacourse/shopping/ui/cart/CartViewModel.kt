package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.inmemory.InMemoryCartRepository

private const val PAGE_SIZE = 5

class CartViewModel(
    private val cartRepository: CartRepository = InMemoryCartRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState(isLoading = true))
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadPage(1)
    }

    fun loadPreviousPage() {
        val currentPage = _uiState.value.currentPage
        if(_uiState.value.isLoading || currentPage <= 1) return
        loadPage(currentPage-1)
    }

    fun loadNextPage() {
        val currentPage = _uiState.value.currentPage
        if(_uiState.value.isLoading || !_uiState.value.hasNext) return
        loadPage(currentPage+1)
    }

    fun delete(product: Product) {
        if(_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            cartRepository.delete(product)

            val remainingCount = cartRepository.count()
            val totalPages = calculateTotalPages(remainingCount)
            val nextPage = _uiState.value.currentPage.coerceAtMost(maxOf(totalPages,1))

            updatePage(nextPage, remainingCount)
        }
    }

    private fun loadPage(page: Int) {
        if(_uiState.value.isLoading && _uiState.value.cart.items.isNotEmpty()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val totalCount = cartRepository.count()
            updatePage(page, totalCount)
        }
    }

    private suspend fun updatePage(
        page: Int,
        totalCount: Int,
    ) {
        val totalPages = calculateTotalPages(totalCount)
        val currentPage = page.coerceIn(1, maxOf(totalPages, 1))
        val fromIndex = (currentPage-1) * PAGE_SIZE
        val items = cartRepository.getCartItems(fromIndex, PAGE_SIZE)

        val cart = Cart(items)

        _uiState.value = _uiState.value.copy(
            cart = cart,
            currentPage = currentPage,
            totalPages = totalPages,
            hasPrevious = currentPage > 1,
            hasNext = currentPage < totalPages,
            isLoading = false,
        )
    }

    private fun calculateTotalPages(totalCount: Int): Int {
        if(totalCount == 0) return 0
        return (totalCount-1)/PAGE_SIZE+1
    }
}