package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import woowacourse.shopping.data.localdb.mapper.toDomain
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.CartResult
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.ui.model.mapper.toUiModel

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    private var page = 0
    private var cart = Cart()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.observeCartItems().collect { entities ->
                val result =
                    runCatching {
                        Cart(
                            items =
                                entities.map { entity ->
                                    val product = productRepository.getProductById(entity.id)
                                    entity.toDomain(product)
                                },
                        )
                    }.fold(
                        onSuccess = { CartResult.Success(it) },
                        onFailure = { CartResult.Failure(it) },
                    )

                when (result) {
                    is CartResult.Success -> {
                        cart = result.cart
                        updateUiState()
                    }

                    is CartResult.Failure -> {
                        _uiState.value =
                            _uiState.value.copy(
                                errorMessage = "장바구니 상품 정보를 불러오지 못했습니다.",
                            )
                    }
                }
            }
        }
    }

    private fun updateUiState() {
        val cartPage = cart.getPage(page = page, pageSize = 5)
        page = cartPage.page
        _uiState.value =
            _uiState.value.copy(
                items = cartPage.items.map { it.toUiModel() }.toImmutableList(),
                page = cartPage.page,
                isCanMoveNext = cartPage.isCanMoveNext,
                totalCartSize = cart.getTotalSize(),
                totalPrice = cart.calculateTotalPrice(),
                errorMessage = null,
            )
    }

    fun nextPage() {
        page++
        updateUiState()
    }

    fun previousPage() {
        page--
        updateUiState()
    }

    fun deleteItem(productId: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(productId)
        }
    }

    fun updateQuantity(
        productId: String,
        quantity: Int,
    ) {
        viewModelScope.launch {
            cartRepository.updateQuantity(productId, quantity = quantity)
        }
    }

    companion object {
        fun provideFactory(
            cartRepository: CartRepository,
            productRepository: ProductRepository,
        ): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    CartViewModel(
                        cartRepository = cartRepository,
                        productRepository = productRepository,
                    )
                }
            }
        }
}

private fun Cart.getPage(
    page: Int,
    pageSize: Int,
): CartPage {
    val lastPage =
        if (items.isEmpty()) {
            0
        } else {
            items.lastIndex / pageSize
        }
    val currentPage = page.coerceIn(0, lastPage)
    val fromIndex = currentPage * pageSize
    val toIndex = minOf(fromIndex + pageSize, items.size)

    return CartPage(
        items = items.subList(fromIndex, toIndex),
        page = currentPage,
        isCanMoveNext = toIndex < items.size,
    )
}

private data class CartPage(
    val items: List<CartItem>,
    val page: Int,
    val isCanMoveNext: Boolean,
)
