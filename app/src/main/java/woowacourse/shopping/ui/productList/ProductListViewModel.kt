package woowacourse.shopping.ui.productList

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.repository.product.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val accumulatedProducts = mutableListOf<Product>()

    init {
        loadNextPage()
    }

    fun moreProducts() {
        loadNextPage()
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            runCatching { productRepository.getProducts(currentPage, PAGE_SIZE) }
                .onSuccess { newProducts ->
                    accumulatedProducts.addAll(newProducts)
                    currentPage++
                    _uiState.value = ProductListUiState.Success(
                        products = accumulatedProducts.toList(),
                        canLoadMore = newProducts.size == PAGE_SIZE,
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = ProductListUiState.Error(throwable)
                }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(productRepository: ProductRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ProductListViewModel(productRepository)
                }
            }
    }
}
