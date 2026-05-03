package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.product.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var currentProductCount = INITIAL_PRODUCT_COUNT

    init {
        loadProducts()
    }

    fun moreProducts() {
        currentProductCount += PRODUCT_COUNT_INCREMENT
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            runCatching { productRepository.getProducts() }
                .onSuccess { products ->
                    _uiState.value =
                        ProductListUiState.Success(
                            products = products,
                            currentProductCount = currentProductCount,
                            totalProductCount = products.size,
                        )
                }
                .onFailure { throwable ->
                    _uiState.value = ProductListUiState.Error(throwable)
                }
        }
    }

    companion object {
        private const val INITIAL_PRODUCT_COUNT = 20
        private const val PRODUCT_COUNT_INCREMENT = 20
    }
}
