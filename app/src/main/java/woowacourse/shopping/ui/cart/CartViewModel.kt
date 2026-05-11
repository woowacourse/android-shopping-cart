package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.common.paging.Pager

class CartViewModel(
    private val cartRepo: CartRepository,
    private val pageSize: Int,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()
    val pager = Pager(pageSize)

    init {
        loadData()
    }

    fun increase(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.increase(product)
                loadData()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun decrease(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.decrease(product)
                loadData()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.delete(product)
                loadData()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun nextPage() {
        val currentPage = _uiState.value.currentPage
        val totalCount = _uiState.value.totalItemCount
        if (pager.hasNext(currentPage, totalCount)) {
            _uiState.update { it.copy(currentPage = currentPage + 1) }
            loadData()
        }
    }

    fun previousPage() {
        val currentPage = _uiState.value.currentPage
        if (pager.hasPrevious(currentPage)) {
            _uiState.update { it.copy(currentPage = currentPage - 1) }
            loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                refreshData()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun refreshData() {
        val totalCount = cartRepo.getSize()
        val totalPages = pager.getTotalPages(totalCount)
        val validCurrentPage = _uiState.value.currentPage.coerceIn(1, totalPages)

        val items =
            cartRepo.getPagedItems(
                fromIndex = pager.getOffset(validCurrentPage),
                count = pageSize,
            )

        _uiState.update {
            it.copy(
                currentPage = validCurrentPage,
                pagedItems = items,
                totalItemCount = totalCount,
                pageSize = pageSize,
            )
        }
    }
}
