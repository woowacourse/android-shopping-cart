package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.common.paging.Pager
import java.util.UUID

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
        val currentQuantity = currentQuantityOf(product.id) ?: return
        viewModelScope.launch {
            try {
                updateQuantity(product = product, quantity = currentQuantity + 1)
            } finally {
            }
        }
    }

    fun decrease(product: Product) {
        val currentQuantity = currentQuantityOf(product.id) ?: return
        viewModelScope.launch {
            try {
                if (currentQuantity <= 1) {
                    cartRepo.delete(product)
                    refreshData()
                } else {
                    updateQuantity(product = product, quantity = currentQuantity - 1)
                }
            } finally {
            }
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                cartRepo.delete(product)
                refreshData()
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

    private fun currentQuantityOf(productId: UUID): Int? =
        _uiState.value.pagedItems
            .find { it.product.id == productId }
            ?.quantity

    private suspend fun updateQuantity(
        product: Product,
        quantity: Int,
    ) {
        cartRepo.setQuantity(item = product, quantity = quantity)
        refreshData()
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

    companion object {
        fun provideFactory(
            cartRepo: CartRepository,
            pageSize: Int,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    CartViewModel(
                        cartRepo = cartRepo,
                        pageSize = pageSize,
                    ) as T
            }
    }
}
