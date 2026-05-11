package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.CartRepository
import kotlin.math.ceil


class CartViewModel(
    private val cartRepo: CartRepository,
    private val pageSize: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

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
        val currentState = _uiState.value
        if (currentState.currentPage < currentState.totalPages) {
            _uiState.update { it.copy(currentPage = currentState.currentPage + 1) }
            loadData()
        }
    }

    fun previousPage() {
        val currentState = _uiState.value
        if (currentState.currentPage > 1) {
            _uiState.update { it.copy(currentPage = currentState.currentPage - 1) }
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
        val count = cartRepo.getSize()
        val maxPage = maxOf(1, ceil(count.toDouble() / pageSize).toInt())
        val validCurrentPage = if (_uiState.value.currentPage > maxPage) {
            maxPage
        } else {
            _uiState.value.currentPage
        }
        val items = cartRepo.getPagedItems(
            fromIndex = (validCurrentPage - 1) * pageSize,
            count = pageSize
        )

        _uiState.update {
            it.copy(
                currentPage = validCurrentPage,
                pagedItems = items,
                totalItemCount = count
            )
        }
    }
}
