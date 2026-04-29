package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.repository.product.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _productListUiState = MutableStateFlow<ProductListState>(ProductListState())
    val productListUiState: StateFlow<ProductListState> = _productListUiState

    private var currentProductCount = 20

    init {
        loadProducts()
    }

    fun moreProducts() {
        currentProductCount += 20
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val products = productRepository.getProducts()

            _productListUiState.value =
                ProductListState(
                    products = products,
                    currentProductCount = currentProductCount,
                    totalProductCount = products.size,
                )
        }
    }
}
