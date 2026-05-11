package woowacourse.shopping.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.ui.DisplayText
import woowacourse.shopping.ui.WonMoney
import kotlin.math.max

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private var currentPage = 0
    private val _uiState =
        MutableStateFlow(
            ShoppingCartUiState(
                shoppingCartItems = emptyList(),
                currentPage = currentPage,
                canMoveToPreviousPage = false,
                canMoveToNextPage = false,
            ),
        )

    val uiState = _uiState.asStateFlow()

    fun loadShoppingItems() {
        viewModelScope.launch {
            loadShoppingItems(currentPage)
        }
    }

    fun moveNextPage() {
        viewModelScope.launch {
            loadShoppingItems(currentPage + 1)
        }
    }

    fun movePreviousPage() {
        viewModelScope.launch {
            loadShoppingItems(currentPage - 1)
        }
    }

    fun removeShoppingItem(productId: String) {
        viewModelScope.launch {
            shoppingCartRepository.removeItemFromCart(productId)
            loadShoppingItems(currentPage)
        }
    }

    fun increaseItemQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.addItemToCart(productId, quantity)
            loadShoppingItems(currentPage)
        }
    }

    fun decreaseItemQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            shoppingCartRepository.decreaseItemQuantity(productId, quantity)
            loadShoppingItems(currentPage)
        }
    }

    private suspend fun loadShoppingItems(page: Int) {
        val totalSize = shoppingCartRepository.getTotalSize()
        val lastPage = max(0, totalSize - 1) / PAGE_SIZE
        currentPage = page.coerceIn(0, lastPage)

        val offset = currentPage * PAGE_SIZE
        val shoppingItems = shoppingCartRepository.getCartItems(offset, PAGE_SIZE)

        _uiState.value = _uiState.value.copy(
            shoppingCartItems = shoppingItems.map { cartItem ->
                val product = cartItem.product
                CartItemUiModel(
                    productId = product.id,
                    title = product.getTitle(),
                    imageUrl = product.imageUrl,
                    price = WonMoney(product.getPrice()),
                    quantity = cartItem.quantity.value,
                )
            },
            currentPage = currentPage,
            canMoveToPreviousPage = currentPage > 0,
            canMoveToNextPage = (currentPage + 1) * PAGE_SIZE < totalSize,
        )
    }

    companion object {
        fun factory(shoppingApplication: ShoppingApplication) =
            viewModelFactory {
                initializer {
                    ShoppingCartViewModel(
                        shoppingApplication.shoppingCartRepository,
                    )
                }
            }

        private const val PAGE_SIZE = 5
    }
}
