package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import woowacourse.shopping.presentation.view.cart.event.CartMessageEvent
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _toastEvent = MutableSingleLiveData<CartMessageEvent>()
    val toastEvent: SingleLiveData<CartMessageEvent> = _toastEvent

    private val _cartItems = MutableLiveData<List<CartItemUiModel>>()
    val cartItems: LiveData<List<CartItemUiModel>> = _cartItems

    private val _page = MutableLiveData(DEFAULT_PAGE)
    val page: LiveData<Int> = _page

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val limit: Int = 5

    init {
        fetchCartItems(isNextPage = false)
    }

    fun fetchCartItems(isNextPage: Boolean) {
        val currentPage = _page.value ?: DEFAULT_PAGE
        val newPage = calculatePage(isNextPage, currentPage)
        val newOffset = (newPage - DEFAULT_PAGE) * limit

        cartRepository.getCartItems(limit, newOffset) { result ->
            result
                .onFailure { _toastEvent.postValue(CartMessageEvent.FETCH_CART_ITEMS_FAILURE) }
                .onSuccess { pageableItem ->
                    fetchCartItemsSuccessHandler(
                        pageableItem,
                        newPage,
                    )
                }
        }
    }

    fun deleteCartItem(cartItem: CartItemUiModel) {
        cartRepository.deleteCartItem(cartItem.id) { result ->
            result
                .onSuccess { fetchCartItems(isNextPage = false) }
                .onFailure { _toastEvent.postValue(CartMessageEvent.DELETE_CART_ITEM_FAILURE) }
        }
    }

    private fun calculatePage(
        isNextPage: Boolean,
        currentPage: Int,
    ): Int =
        if (isNextPage) {
            currentPage + DEFAULT_PAGE
        } else {
            max(DEFAULT_PAGE, currentPage - DEFAULT_PAGE)
        }

    private fun fetchCartItemsSuccessHandler(
        pageableItem: PageableItem<CartItem>,
        newPage: Int,
    ) {
        val uiModels = pageableItem.items.map { it.toUiModel() }
        _cartItems.postValue(uiModels)
        _hasMore.postValue(pageableItem.hasMore)
        _page.postValue(newPage)
    }

    companion object {
        private const val DEFAULT_PAGE = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val cartRepository = RepositoryProvider.cartRepository
                    return CartViewModel(cartRepository) as T
                }
            }
    }
}
