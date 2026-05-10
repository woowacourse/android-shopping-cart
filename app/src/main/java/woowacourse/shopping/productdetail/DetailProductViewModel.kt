package woowacourse.shopping.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.ui.WonMoney

class DetailProductViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ProductUiModel(
                id = "-1",
                name = "존재하지 않는 상품",
                price = WonMoney(-9999),
                imageUrl = "키키 - 404(New Era)",
                quantity = 0
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val product = productRepository.getProduct(productId) ?: return@launch

            val shoppingCartItem =
                shoppingCartRepository.getItemByProductId(productId)

            val quantity = shoppingCartItem?.quantity?.value ?: 0

            _uiState.value =
                ProductUiModel(
                    id = product.id,
                    name = product.getTitle(),
                    price = WonMoney(product.getPrice()),
                    imageUrl = product.imageUrl,
                    quantity = quantity
                )
        }
    }

    fun increaseQuantity(quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.increaseItemQuantityByProductId(_uiState.value.id, Quantity(quantity))
            updateQuantity(_uiState.value.id)
        }
    }

    fun decreaseQuantity(quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseItemQuantityByProductId(_uiState.value.id, Quantity(quantity))
            updateQuantity(_uiState.value.id)
        }
    }

    private suspend fun updateQuantity(productId: String) {
        val shoppingCartItem = shoppingCartRepository.getItemByProductId(productId)
        if (shoppingCartItem == null) {
            _uiState.value = _uiState.value.copy(
                quantity = 0,
            )
            return
        }
        _uiState.value = _uiState.value.copy(
            quantity = shoppingCartItem.quantity.value,
        )
    }

    companion object {
        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    DetailProductViewModel(
                        shoppingApplication.productRepository,
                        shoppingApplication.shoppingCartRepository,
                    )
                }
            }
    }
}
