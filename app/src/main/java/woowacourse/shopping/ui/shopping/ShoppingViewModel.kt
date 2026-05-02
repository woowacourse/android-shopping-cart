package woowacourse.shopping.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository

private const val PAGE_SIZE = 20

class ShoppingViewModel(
    private val productRepository: ProductRepository = InMemoryProductRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState(isLoading = true))
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var visibleCount = PAGE_SIZE

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val products = productRepository.getProducts(0, visibleCount)
            val hasNext = productRepository.hasNext(products.count()-1)

            _uiState.value = ShoppingUiState(
                products = products,
                hasNext = hasNext,
                isLoading = false,
            )
        }
    }

    fun loadMore() {
        val currentState= _uiState.value
        if(currentState.isLoading || !currentState.hasNext) return
        visibleCount = minOf(visibleCount + PAGE_SIZE, productRepository.size)
        loadProducts()
    }
}