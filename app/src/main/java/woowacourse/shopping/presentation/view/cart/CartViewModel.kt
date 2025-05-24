package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.di.provider.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.model.FetchPageDirection
import woowacourse.shopping.presentation.model.toCartItemUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.cart.event.CartMessageEvent
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CartMessageEvent>()
    val toastEvent: SingleLiveData<CartMessageEvent> = _toastEvent

    private val _cartItems = MutableLiveData<List<CartItemUiModel>>(emptyList())
    val cartItems: LiveData<List<CartItemUiModel>> = _cartItems

    private val _page = MutableLiveData(DEFAULT_PAGE)
    val page: LiveData<Int> = _page

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val limit = 5

    init {
        fetchCartItems(FetchPageDirection.CURRENT)
    }

    fun fetchCartItems(direction: FetchPageDirection) {
        val newPage = calculatePage(direction)
        val offset = (newPage - DEFAULT_PAGE) * limit

        cartRepository.loadCartItems(offset, limit) { result ->
            result
                .onSuccess { handleFetchCartItemsSuccess(it, newPage) }
                .onFailure { postFailureEvent(CartMessageEvent.FETCH_CART_ITEMS_FAILURE) }
        }
    }

    fun deleteCartItem(item: CartItemUiModel) {
        cartRepository.deleteCartItem(item.productId) { result ->
            result
                .onSuccess { handleFetchCartItemDeleted(item.productId) }
                .onFailure { postFailureEvent(CartMessageEvent.DELETE_CART_ITEM_FAILURE) }
        }
    }

    fun addProductToCart(productId: Long) {
        cartRepository.addCartItem(productId, QUANTITY_STEP) { result ->
            result
                .onSuccess { refreshProductQuantity(productId) }
                .onFailure { postFailureEvent(CartMessageEvent.INCREASE_PRODUCT_TO_CART_FAILURE) }
        }
    }

    fun removeProductFromCart(productId: Long) {
        cartRepository.decreaseCartItemQuantity(productId) { result ->
            result
                .onSuccess { refreshProductQuantity(productId) }
                .onFailure { postFailureEvent(CartMessageEvent.DECREASE_PRODUCT_FROM_CART_FAILURE) }
        }
    }

    private fun refreshProductQuantity(productId: Long) {
        cartRepository.findCartItemByProductId(productId) { result ->
            result
                .onSuccess { cartItem ->
                    updateItemQuantityInList(cartItem)
                }.onFailure {
                    if (it is NoSuchElementException) {
                        handleFetchCartItemDeleted(productId)
                        return@onFailure
                    }
                    postFailureEvent(CartMessageEvent.FIND_PRODUCT_QUANTITY_FAILURE)
                }
        }
    }

    private fun updateItemQuantityInList(updatedCartItem: CartItem) {
        val items = _cartItems.value.orEmpty()
        val updatedItems =
            items.map { item ->
                if (item.productId != updatedCartItem.product.id) return@map item
                updatedCartItem.toCartItemUiModel()
            }
        _cartItems.postValue(updatedItems)
    }

    private fun handleFetchCartItemsSuccess(
        pageableItem: PageableItem<CartItem>,
        newPage: Int,
    ) {
        _cartItems.postValue(pageableItem.items.map { it.toCartItemUiModel() })
        _hasMore.postValue(pageableItem.hasMore)
        _page.postValue(newPage)
    }

    private fun handleFetchCartItemDeleted(deletedProductId: Long) {
        val items = _cartItems.value.orEmpty()
        val isLastItem = items.size == 1 && items.first().productId == deletedProductId

        fetchCartItems(if (isLastItem) FetchPageDirection.PREVIOUS else FetchPageDirection.CURRENT)
    }

    private fun calculatePage(direction: FetchPageDirection): Int {
        val currentPage = _page.value ?: DEFAULT_PAGE
        return when (direction) {
            FetchPageDirection.PREVIOUS -> max(DEFAULT_PAGE, currentPage - PAGE_STEP)
            FetchPageDirection.CURRENT -> currentPage
            FetchPageDirection.NEXT -> currentPage + PAGE_STEP
        }
    }

    private fun postFailureEvent(event: CartMessageEvent) {
        _toastEvent.postValue(event)
    }

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val PAGE_STEP = 1
        private const val QUANTITY_STEP = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T = CartViewModel(RepositoryProvider.cartRepository) as T
            }
    }
}
