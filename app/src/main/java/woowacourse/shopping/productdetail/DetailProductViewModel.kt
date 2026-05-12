package woowacourse.shopping.productdetail

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
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.productlist.ViewedProductUiModel
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ViewedProductRepository
import woowacourse.shopping.ui.WonMoney
import kotlin.math.max

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

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId) ?: return@launch

            _uiState.update {
                DetailProductUiState(
                    productUiModel = product.toUiModel(),
                    lastViewedProductUiModel = getMostRecentlyViewedProductUiModel(product.id),
                )
            }

            viewedProductRepository.addViewedProduct(product.id)
        }
    }

    fun increaseItemQuantity(quantity: Int) {
        updateQuantity(quantity)
    }

    fun decreaseItemQuantity(quantity: Int) {
        updateQuantity(-quantity)
    }

    fun addSelectedQuantityToCart(onComplete: () -> Unit) {
        viewModelScope.launch {
            val product = _uiState.value.productUiModel
            shoppingCartRepository.addItemToCart(product.id, product.quantity)
            onComplete()
        }
    }

    private fun updateQuantity(quantity: Int) {
        _uiState.update { state ->
            state.copy(
                productUiModel =
                    state.productUiModel.copy(
                        quantity = max(1, state.productUiModel.quantity + quantity),
                    ),
            )
        }
    }

    private suspend fun getMostRecentlyViewedProductUiModel(productId: String): ViewedProductUiModel? {
        val viewedProduct =
            viewedProductRepository
                .getRecentlyViewedProducts(offset = 0, size = 1)
                .firstOrNull { viewedProduct -> viewedProduct.product.id != productId } ?: return null
        return ViewedProductUiModel(
            id = viewedProduct.product.id,
            name = viewedProduct.product.getTitle(),
            imageUrl = viewedProduct.product.imageUrl,
            viewedAt = viewedProduct.viewedAt,
        )
    }

    private fun Product.toUiModel(): ProductUiModel =
        ProductUiModel(
            id = id,
            name = getTitle(),
            price = WonMoney(getPrice()),
            imageUrl = imageUrl,
            quantity = 1,
        )

    companion object {
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
