package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.ProductRepository

class ShoppingViewModel(
    private val productRepo: ProductRepository,
    private val loadSize: Int,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val initialProducts = productRepo.getProducts(0, loadSize)
                val hasNextPage = productRepo.hasNext(initialProducts.lastIndex)
                val totalSize = productRepo.getSize()

                _uiState.update {
                    it.copy(
                        visibleProducts = initialProducts,
                        hasNext = hasNextPage,
                        sizeInRepo = totalSize
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val currentProducts = _uiState.value.visibleProducts
                val newProducts = productRepo.getProducts(
                    fromIndex = _uiState.value.visibleProducts.size,
                    count = loadSize,
                )
                val combineProducts = currentProducts + newProducts

                val hasNextPage = productRepo.hasNext(combineProducts.lastIndex)
                val totalSize = productRepo.getSize()

                _uiState.update {
                    it.copy(
                        visibleCount = minOf(it.visibleCount + loadSize, totalSize),
                        visibleProducts = combineProducts,
                        hasNext = hasNextPage,
                        sizeInRepo = totalSize
                    )
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
