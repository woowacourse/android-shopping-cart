package woowacourse.shopping.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.ui.DisplayText
import woowacourse.shopping.ui.WonMoney

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: DisplayText,
    val quantity: Int,
    val imageUrl: String,
)

data class ProductListUiState(
    val productUiModels: List<ProductUiModel>,
    val cartItemCount: Int,
    val enableMoreButton: Boolean,
)

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductListUiState(
                productUiModels = emptyList(),
                enableMoreButton = false,
                cartItemCount = 0,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            val currentProductSize = _uiState.value.productUiModels.size
            val updatedProductUiModels =
                _uiState.value.productUiModels +
                    productRepository.getProducts(currentProductSize, 20).map { product ->
                        val cartItem = shoppingCartRepository.getItemByProductId(product.id)
                        val quantity = cartItem?.quantity?.value ?: 0
                        ProductUiModel(
                            id = product.id,
                            name = product.getTitle(),
                            price = WonMoney(product.getPrice()),
                            imageUrl = product.imageUrl,
                            quantity = quantity
                        )
                    }

            _uiState.value =
                _uiState.value.copy(
                    productUiModels = updatedProductUiModels,
                    enableMoreButton = updatedProductUiModels.size < productRepository.totalSize(),
                    cartItemCount = shoppingCartRepository.getTotalQuantity(),
                )
        }
    }

    fun refreshProducts(productIds: List<String>) {
        viewModelScope.launch {
            val changedProductQuantities =
                productIds.distinct().associateWith { productId ->
                    shoppingCartRepository.getItemByProductId(productId)?.quantity?.value ?: 0
                }

            _uiState.value =
                _uiState.value.copy(
                    productUiModels =
                        _uiState.value.productUiModels.map { item ->
                            changedProductQuantities[item.id]?.let { quantity ->
                                item.copy(quantity = quantity)
                            } ?: item
                        },
                    cartItemCount = shoppingCartRepository.getTotalQuantity(),
                )
        }
    }

    fun increaseItemQuantity(productId: String) {
        viewModelScope.launch {
            shoppingCartRepository.increaseItemQuantityByProductId(productId, Quantity(1))
            updateProduct(productId)
        }
    }

    fun decreaseItemQuantity(productId: String) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseItemQuantityByProductId(productId, Quantity(1))
            updateProduct(productId)
        }
    }

    private suspend fun updateProduct(productId: String) {
        val cartItem = shoppingCartRepository.getItemByProductId(productId)

        _uiState.value =
            _uiState.value.copy(
                productUiModels =
                    _uiState.value.productUiModels.map { item ->
                        if (item.id == productId) {
                            item.copy(
                                quantity = cartItem?.quantity?.value ?: 0,
                            )
                        } else {
                            item
                        }
                    },
                cartItemCount = shoppingCartRepository.getTotalQuantity(),
            )
    }

    companion object {
        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    ProductListViewModel(
                        shoppingApplication.productRepository,
                        shoppingApplication.shoppingCartRepository,
                    )
                }
            }
    }
}
