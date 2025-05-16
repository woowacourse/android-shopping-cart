package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.RepositoryProvider.productRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.model.toUiModel
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItemUiModel>>()
    val cartItems: LiveData<List<CartItemUiModel>> = _cartItems

    private val _deleteState = MutableLiveData<Long>()
    val deleteState: LiveData<Long> = _deleteState

    private val _page = MutableLiveData(DEFAULT_PAGE)
    val page: LiveData<Int> = _page

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val limit: Int = 5

    init {
        fetchShoppingCart(false)
    }

    fun fetchShoppingCart(
        isNextPage: Boolean,
        isRefresh: Boolean = false,
    ) {
        val currentPage = _page.value ?: DEFAULT_PAGE

        val newPage = (calculatePage(isNextPage, currentPage, isRefresh))

        val newOffset = (newPage - DEFAULT_PAGE) * limit

        cartRepository.getCartItems(limit = limit, offset = newOffset) { products, hasMore ->
            fetchCartItemsByIds(products)
            _page.postValue(newPage)
            _hasMore.postValue(hasMore)
        }
    }

    fun deleteCartItem(cartItem: CartItemUiModel) {
        cartRepository.deleteCartItem(cartItem.id) {
            _deleteState.postValue(it)
        }
    }

    private fun calculatePage(
        isNextPage: Boolean,
        currentPage: Int,
        isRefresh: Boolean,
    ): Int {
        if (isRefresh) return currentPage

        return if (isNextPage) {
            currentPage + DEFAULT_PAGE
        } else {
            max(
                DEFAULT_PAGE,
                currentPage - DEFAULT_PAGE,
            )
        }
    }

    private fun fetchCartItemsByIds(cartItems: List<CartItem>) {
        val productIds = cartItems.map { it.productId }

        productRepository.findProductsByIds(productIds) { foundProducts ->
            val productMap = foundProducts.associateBy { it.id }

            val cartItemUiModels =
                cartItems.mapNotNull { cartItem ->
                    val product = productMap[cartItem.productId]
                    product?.let { cartItem.toUiModel(it.toUiModel()) }
                }

            _cartItems.postValue(cartItemUiModels)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 1

        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val repository = RepositoryProvider.cartRepository
                    return CartViewModel(repository) as T
                }
            }
    }
}
