package woowacourse.shopping.productlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
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
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductListUiState(
                productUiModels = emptyList(),
                enableMoreButton = false,
            ),
        )

    val uiState = _uiState.asStateFlow()

    private var productIds: List<String>
        get() = savedStateHandle.get<List<String>>(KEY_PRODUCT_IDS) ?: emptyList()
        set(value) {
            savedStateHandle[KEY_PRODUCT_IDS] = value
        }

    fun loadProducts() {
        viewModelScope.launch {
            val newProducts = productRepository.getProducts(productIds.size, 20)

            productIds = productIds + newProducts.map { product -> product.id }

            val updatedProductUiModel =
                _uiState.value.productUiModels +
                    newProducts.map { product ->
                        ProductUiModel(
                            id = product.id,
                            name = product.getTitle(),
                            price = WonMoney(product.getPrice()),
                            imageUrl = product.imageUrl,
                        )
                    }

            _uiState.value =
                _uiState.value.copy(
                    productUiModels = updatedProductUiModel,
                    enableMoreButton = productIds.size < productRepository.totalSize(),
                )
        }
    }

    companion object {
        private const val KEY_PRODUCT_IDS = "productIds"

        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    ProductListViewModel(
                        shoppingApplication.productRepository,
                        savedStateHandle = createSavedStateHandle(),
                    )
                }
            }
    }
}
