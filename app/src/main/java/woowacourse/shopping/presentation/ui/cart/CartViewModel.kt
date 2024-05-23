package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.joinProductAndCart
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.shopping.ShoppingError
import woowacourse.shopping.presentation.util.Event

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    var maxPage: Int = 0
        private set
    var currentPage: Int = 0
        private set
        get() = field.coerceAtMost(maxPage)

    private val _error = MutableLiveData<Event<ShoppingError>>()

    val error: LiveData<Event<ShoppingError>> = _error

    private val _shoppingProducts =
        MutableLiveData<UiState<List<ProductListItem.ShoppingProductItem>>>(UiState.None)

    val shoppingProducts: LiveData<UiState<List<ProductListItem.ShoppingProductItem>>> get() = _shoppingProducts

    private val cartProducts = mutableListOf<Cart>()

    private val _changedCartProducts = MutableLiveData<List<Cart>>()
    val changedCartProducts: LiveData<List<Cart>> get() = _changedCartProducts

    init {
        updateMaxPage()
    }

    fun deleteProduct(product: Product) {
        cartRepository.deleteProduct(product).onSuccess {
            val originalChangedCartProducts = changedCartProducts.value ?: emptyList()
            _changedCartProducts.value = originalChangedCartProducts.plus(Cart(product, 0))
            updateMaxPage()
            loadProductByPage()
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemNotDeleted)
        }
    }

    private fun updateMaxPage() {
        cartRepository.getMaxPage(PAGE_SIZE).onSuccess {
            maxPage = it
        }
    }

    fun loadProductByPage() {
        cartRepository.load(currentPage, PAGE_SIZE).onSuccess {
            cartProducts.apply {
                clear()
                addAll(it)
            }
            _shoppingProducts.value =
                UiState.Success(cartProducts.map { joinProductAndCart(it.product, it) })
        }.onFailure {
            _error.value = Event(ShoppingError.CartItemsNotFound)
        }
    }

    fun plus() {
        if (currentPage == maxPage) return
        currentPage++
        loadProductByPage()
    }

    fun minus() {
        if (currentPage == 0) return
        currentPage--
        loadProductByPage()
    }

    fun updateCartItemQuantity(
        product: Product,
        quantityDelta: Int,
    ) {
        cartRepository.updateQuantity(product, quantityDelta).onSuccess {
            modifyShoppingProductQuantity(product.id, quantityDelta)
        }
    }

    private fun modifyShoppingProductQuantity(
        productId: Long,
        quantityDelta: Int,
    ) {
        val productIndex = cartProducts.indexOfFirst { it.product.id == productId }
        val originalQuantity = cartProducts[productIndex].quantity
        val updatedItem =
            cartProducts[productIndex].copy(
                quantity =
                    (originalQuantity + quantityDelta).coerceAtLeast(
                        0,
                    ),
            )
        cartProducts[productIndex] = updatedItem
        _shoppingProducts.value =
            UiState.Success(cartProducts.map { joinProductAndCart(it.product, it) })
        val originalChangedCartProducts = changedCartProducts.value ?: emptyList()
        _changedCartProducts.value = originalChangedCartProducts.plus(updatedItem)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
