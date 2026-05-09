package woowacourse.shopping.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.ui.DisplayableMoney
import woowacourse.shopping.ui.WonMoney

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: DisplayableMoney,
    val imageUrl: String,
)

data class ProductListUiState(
    val productUiModels: List<ProductUiModel>,
    val enableMoreButton: Boolean,
)

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductListUiState(
                productUiModels = emptyList(),
                enableMoreButton = false,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            val currentProductSize = _uiState.value.productUiModels.size
            val updatedProductUiModels =
                _uiState.value.productUiModels +
                    productRepository.getProducts(currentProductSize, 20).map { product ->
                        ProductUiModel(
                            id = product.id,
                            name = product.getTitle(),
                            price = WonMoney(product.getPrice()),
                            imageUrl = product.imageUrl,
                        )
                    }

            _uiState.value =
                _uiState.value.copy(
                    productUiModels = updatedProductUiModels,
                    enableMoreButton = updatedProductUiModels.size < productRepository.totalSize(),
                )
        }
    }

    companion object {
        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    ProductListViewModel(
                        shoppingApplication.productRepository,
                    )
                }
            }
    }
}
