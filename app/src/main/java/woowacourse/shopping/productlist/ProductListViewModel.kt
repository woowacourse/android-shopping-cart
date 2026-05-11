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
import woowacourse.shopping.ui.WonMoney

data class ProductListUiState(
    val productUiModels: List<ProductUiModel>,
    val viewedProductUiModels: List<ViewedProductUiModel>,
    val cartItemCount: Int,
    val isLoadMoreEnabled: Boolean,
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
                isLoadMoreEnabled = false,
                cartItemCount = 0,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            val viewedProductUiModels = getViewedProductUiModels()
            val productUiModels = getProductUiModels(offset = 0, size = PRODUCT_LOAD_SIZE)
            val totalProductSize = productRepository.totalSize()
            val totalCartQuantity = shoppingCartRepository.getTotalQuantity()

            _uiState.update { currentState ->
                currentState.copy(
                    productUiModels = productUiModels,
                    viewedProductUiModels = viewedProductUiModels,
                    isLoadMoreEnabled = productUiModels.size < totalProductSize,
                    cartItemCount = totalCartQuantity,
                )
            }
        }
    }

    fun loadViewedProducts() {
        viewModelScope.launch {
            val viewedProductUiModels = getViewedProductUiModels()

            _uiState.update { uiState ->
                uiState.copy(
                    viewedProductUiModels = viewedProductUiModels,
                )
            }
        }
    }

    fun loadMoreProducts() {
        viewModelScope.launch {
            val currentProductSize = _uiState.value.productUiModels.size
            val updatedProductUiModels =
                _uiState.value.productUiModels +
                    getProductUiModels(offset = currentProductSize, size = PRODUCT_LOAD_SIZE)
            val totalProductSize = productRepository.totalSize()
            val totalCartQuantity = shoppingCartRepository.getTotalQuantity()

            _uiState.update { currentState ->
                currentState.copy(
                    productUiModels = updatedProductUiModels,
                    isLoadMoreEnabled = updatedProductUiModels.size < totalProductSize,
                    cartItemCount = totalCartQuantity,
                )
            }
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            val updatedProductUiModels =
                _uiState.value.productUiModels.map { productUiModel ->
                    productUiModel.copy(
                        quantity = getCartQuantity(productUiModel.id),
                    )
                }
            val totalCartQuantity = shoppingCartRepository.getTotalQuantity()

            _uiState.update { uiState ->
                uiState.copy(
                    productUiModels = updatedProductUiModels,
                    cartItemCount = totalCartQuantity,
                )
            }
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
        val updatedProductUiModels =
            _uiState.value.productUiModels.map { productUiModel ->
                if (productUiModel.id != productId) {
                    productUiModel
                } else {
                    productUiModel.copy(
                        quantity = getCartQuantity(productUiModel.id),
                    )
                }
            }
        val totalCartQuantity = shoppingCartRepository.getTotalQuantity()

        _uiState.update { uiState ->
            uiState.copy(
                productUiModels = updatedProductUiModels,
                cartItemCount = totalCartQuantity,
            )
        }
    }

    private suspend fun getViewedProductUiModels(): List<ViewedProductUiModel> =
        viewedProductRepository
            .getRecentlyViewedProducts(0, VIEWED_PRODUCT_SIZE)
            .map { viewedProduct ->
                ViewedProductUiModel(
                    id = viewedProduct.product.id,
                    name = viewedProduct.product.getTitle(),
                    imageUrl = viewedProduct.product.imageUrl,
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

    private suspend fun Product.toUiModel(): ProductUiModel =
        ProductUiModel(
            id = id,
            name = getTitle(),
            price = WonMoney(getPrice()),
            imageUrl = imageUrl,
            quantity = getCartQuantity(id),
        )

    private suspend fun getCartQuantity(productId: String): Int = shoppingCartRepository.getCartItem(productId)?.quantity?.value ?: 0

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
