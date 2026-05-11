package woowacourse.shopping.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ViewedProductRepository
import woowacourse.shopping.ui.DisplayText
import woowacourse.shopping.ui.WonMoney

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: DisplayText,
    val quantity: Int,
    val imageUrl: String,
)

data class ViewedProductUiModel(
    val productUiModel: ProductUiModel,
    val viewedAt: Long,
)

data class ProductListUiState(
    val productUiModels: List<ProductUiModel>,
    val viewedProductUiModels: List<ViewedProductUiModel>,
    val cartItemCount: Int,
    val enableMoreButton: Boolean,
)

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val viewedProductRepository: ViewedProductRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductListUiState(
                productUiModels = emptyList(),
                viewedProductUiModels = emptyList(),
                enableMoreButton = false,
                cartItemCount = 0,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadInitialProducts() {
        viewModelScope.launch {
            val viewedProductUiModels = getViewedProductUiModels()
            val productUiModels = getProductUiModels(offset = 0, size = PRODUCT_LOAD_SIZE)

            _uiState.update { currentState ->
                currentState.copy(
                    productUiModels = productUiModels,
                    viewedProductUiModels = viewedProductUiModels,
                    enableMoreButton = productUiModels.size < productRepository.totalSize(),
                    cartItemCount = shoppingCartRepository.getTotalQuantity(),
                )
            }
        }
    }

    fun loadViewedProducts() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    viewedProductUiModels = getViewedProductUiModels(),
                )
            }
        }
    }

    fun refreshDisplayedProducts() {
        viewModelScope.launch {
            val productIds =
                (
                    _uiState.value.productUiModels.map { it.id } +
                        _uiState.value.viewedProductUiModels.map { it.productUiModel.id }
                ).distinct()
            val changedProductQuantities = getProductQuantities(productIds)

            updateProductQuantities(changedProductQuantities)
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            val currentProductSize = _uiState.value.productUiModels.size
            val updatedProductUiModels =
                _uiState.value.productUiModels +
                    getProductUiModels(offset = currentProductSize, size = PRODUCT_LOAD_SIZE)

            _uiState.update { currentState ->
                currentState.copy(
                    productUiModels = updatedProductUiModels,
                    enableMoreButton = updatedProductUiModels.size < productRepository.totalSize(),
                    cartItemCount = shoppingCartRepository.getTotalQuantity(),
                )
            }
        }
    }

    fun refreshProducts(productIds: List<String>) {
        viewModelScope.launch {
            val changedProductQuantities = getProductQuantities(productIds.distinct())

            updateProductQuantities(changedProductQuantities)
        }
    }

    fun increaseItemQuantity(productId: String) {
        viewModelScope.launch {
            shoppingCartRepository.addItemToCart(productId, 1)
            updateProduct(productId)
        }
    }

    fun decreaseItemQuantity(productId: String) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseItemQuantity(productId, 1)
            updateProduct(productId)
        }
    }

    private suspend fun updateProduct(productId: String) {
        val cartItem = shoppingCartRepository.getCartItem(productId)

        _uiState.update { currentState ->
            currentState.copy(
                productUiModels =
                    currentState.productUiModels.map { item ->
                        if (item.id == productId) {
                            item.copy(
                                quantity = cartItem?.quantity?.value ?: 0,
                            )
                        } else {
                            item
                        }
                    },
                viewedProductUiModels =
                    currentState.viewedProductUiModels.map { item ->
                        if (item.productUiModel.id == productId) {
                            item.copy(
                                productUiModel =
                                    item.productUiModel.copy(
                                        quantity = cartItem?.quantity?.value ?: 0,
                                    ),
                            )
                        } else {
                            item
                        }
                    },
                cartItemCount = shoppingCartRepository.getTotalQuantity(),
            )
        }
    }

    private suspend fun getProductQuantities(productIds: List<String>): Map<String, Int> =
        productIds.associateWith { productId ->
            shoppingCartRepository.getCartItem(productId)?.quantity?.value ?: 0
        }

    private suspend fun updateProductQuantities(changedProductQuantities: Map<String, Int>) {
        _uiState.update { currentState ->
            currentState.copy(
                productUiModels =
                    currentState.productUiModels.map { item ->
                        changedProductQuantities[item.id]?.let { quantity ->
                            item.copy(quantity = quantity)
                        } ?: item
                    },
                viewedProductUiModels =
                    currentState.viewedProductUiModels.map { item ->
                        changedProductQuantities[item.productUiModel.id]?.let { quantity ->
                            item.copy(
                                productUiModel = item.productUiModel.copy(quantity = quantity),
                            )
                        } ?: item
                    },
                cartItemCount = shoppingCartRepository.getTotalQuantity(),
            )
        }
    }

    private suspend fun getViewedProductUiModels(): List<ViewedProductUiModel> =
        viewedProductRepository.getViewedProducts(0, VIEWED_PRODUCT_SIZE).map { viewedProduct ->
            ViewedProductUiModel(
                productUiModel = viewedProduct.product.toUiModel(),
                viewedAt = viewedProduct.viewedAt,
            )
        }

    private suspend fun getProductUiModels(
        offset: Int,
        size: Int,
    ): List<ProductUiModel> =
        productRepository.getProducts(offset, size).map { product ->
            product.toUiModel()
        }

    private suspend fun Product.toUiModel(): ProductUiModel {
        val cartItem = shoppingCartRepository.getCartItem(id)
        return ProductUiModel(
            id = id,
            name = getTitle(),
            price = WonMoney(getPrice()),
            imageUrl = imageUrl,
            quantity = cartItem?.quantity?.value ?: 0,
        )
    }

    companion object {
        private const val PRODUCT_LOAD_SIZE = 20
        private const val VIEWED_PRODUCT_SIZE = 10

        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    ProductListViewModel(
                        shoppingApplication.productRepository,
                        shoppingApplication.shoppingCartRepository,
                        shoppingApplication.viewedProductRepository,
                    )
                }
            }
    }
}
