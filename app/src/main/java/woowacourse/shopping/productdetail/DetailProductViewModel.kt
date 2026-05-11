package woowacourse.shopping.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import woowacourse.shopping.model.Product
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ViewedProductRepository
import woowacourse.shopping.ui.WonMoney

data class DetailProductUiState(
    val productUiModel: ProductUiModel,
    val lastViewedProductUiModel: ProductUiModel?,
)

class DetailProductViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val viewedProductRepository: ViewedProductRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            DetailProductUiState(
                productUiModel =
                    ProductUiModel(
                        id = "-1",
                        name = "존재하지 않는 상품",
                        price = WonMoney(-9999),
                        imageUrl = "키키 - 404(New Era)",
                        quantity = 0,
                    ),
                lastViewedProductUiModel = null,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProduct(
        productId: String,
        hideLastViewedProduct: Boolean,
    ) {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId) ?: return@launch
            val lastViewedProductUiModel =
                if (hideLastViewedProduct) {
                    null
                } else {
                    getLastViewedProductUiModel(product.id)
                }
            viewedProductRepository.addViewedProductByProductId(product.id)

            _uiState.value =
                DetailProductUiState(
                    productUiModel = product.toUiModel(),
                    lastViewedProductUiModel = lastViewedProductUiModel,
                )
        }
    }

    fun increaseQuantity(quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.addItemToCart(_uiState.value.productUiModel.id, quantity)
            updateQuantity(_uiState.value.productUiModel.id)
        }
    }

    fun decreaseQuantity(quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseItemQuantity(_uiState.value.productUiModel.id, quantity)
            updateQuantity(_uiState.value.productUiModel.id)
        }
    }

    private suspend fun updateQuantity(productId: String) {
        val shoppingCartItem = shoppingCartRepository.getCartItem(productId)
        if (shoppingCartItem == null) {
            _uiState.update { state ->
                state.copy(
                    productUiModel = state.productUiModel.copy(quantity = 0),
                )
            }
            return
        }
        _uiState.update { state ->
            state.copy(
                productUiModel = state.productUiModel.copy(quantity = shoppingCartItem.quantity.value),
            )
        }
    }

    private suspend fun getLastViewedProductUiModel(currentProductId: String): ProductUiModel? =
        viewedProductRepository
            .getViewedProducts(offset = 0, size = LAST_VIEWED_PRODUCT_SEARCH_SIZE)
            .firstOrNull { viewedProduct -> viewedProduct.product.id != currentProductId }
            ?.product
            ?.toUiModel()

    private suspend fun Product.toUiModel(): ProductUiModel {
        val shoppingCartItem = shoppingCartRepository.getCartItem(id)
        return ProductUiModel(
            id = id,
            name = getTitle(),
            price = WonMoney(getPrice()),
            imageUrl = imageUrl,
            quantity = shoppingCartItem?.quantity?.value ?: 0,
        )
    }

    companion object {
        private const val LAST_VIEWED_PRODUCT_SEARCH_SIZE = 10

        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    DetailProductViewModel(
                        shoppingApplication.productRepository,
                        shoppingApplication.shoppingCartRepository,
                        shoppingApplication.viewedProductRepository,
                    )
                }
            }
    }
}
