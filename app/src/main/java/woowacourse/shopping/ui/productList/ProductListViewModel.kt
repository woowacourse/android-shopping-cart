package woowacourse.shopping.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.ProductRepositoryMockImpl

class ProductListViewModel(
    private val productRepository: ProductRepository = ProductRepositoryMockImpl(),
) : ViewModel() {
    private val _productListUiState = MutableStateFlow<ProductListState>(ProductListState())
    val productListUiState: StateFlow<ProductListState> = _productListUiState

    private var currentPage = 0

    init {
        loadProducts()
    }

    fun moreProducts() {
        currentPage += 1
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val totalProductCount = productRepository.getProductsSize()

            _productListUiState.value =
                ProductListState(
                    products = productListUiState.value.products + productRepository.getPagedProducts(page = currentPage, pageSize = PAGE_SIZE),
                    currentProductCount = currentPage,
                    totalProductCount = totalProductCount,
                )
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
