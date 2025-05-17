package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.model.CartItemUiModel
import woowacourse.shopping.presentation.model.toUiModel
import woowacourse.shopping.presentation.util.MutableSingleLiveData
import woowacourse.shopping.presentation.util.SingleLiveData
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItemUiModel>>()
    val cartItems: LiveData<List<CartItemUiModel>> = _cartItems

    private val _page = MutableLiveData(DEFAULT_PAGE)
    val page: LiveData<Int> = _page

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val _deleteEvent = MutableSingleLiveData<Long>()
    val deleteEvent: SingleLiveData<Long> = _deleteEvent

    private val _refreshEvent = MutableSingleLiveData<List<CartItemUiModel>>()
    val refreshEvent: SingleLiveData<List<CartItemUiModel>> = _refreshEvent

    private val limit: Int = 5

    init {
        fetchCartItems(isNextPage = false)
    }

    fun fetchCartItems(
        isNextPage: Boolean,
        isRefresh: Boolean = false,
    ) {
        val currentPage = _page.value ?: DEFAULT_PAGE
        val newPage = if (isRefresh) currentPage else calculatePage(isNextPage, currentPage)
        val newOffset = (newPage - DEFAULT_PAGE) * limit

        cartRepository.getCartItems(limit, newOffset) { cartItems, hasMore ->
            loadProductDetails(cartItems, isRefresh)
            _page.postValue(newPage)
            _hasMore.postValue(hasMore)
        }
    }

    fun deleteCartItem(cartItem: CartItemUiModel) {
        cartRepository.deleteCartItem(cartItem.id) { deletedId ->
            _deleteEvent.postValue(deletedId)
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

    private fun loadProductDetails(
        cartItems: List<CartItem>,
        isRefresh: Boolean,
    ) {
        val productIds = cartItems.map { it.productId }

        productRepository.findProductsByIds(productIds) { foundProducts ->
            val productMap = foundProducts.associateBy { it.id }
            val uiModels = cartItems.mapUiModels(productMap)

            if (uiModels.isEmpty()) return@findProductsByIds fetchCartItems(isNextPage = false)

            uiModels.processRefreshEvent(isRefresh)
        }
    }

    private fun List<CartItem>.mapUiModels(productMap: Map<Long, Product>) =
        this.mapNotNull { cartItem ->
            productMap[cartItem.productId]?.let { product ->
                cartItem.toUiModel(product.toUiModel())
            }
        }

    private fun List<CartItemUiModel>.processRefreshEvent(isRefresh: Boolean) {
        if (isRefresh) _refreshEvent.postValue(this) else _cartItems.postValue(this)
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
                    val productRepository = RepositoryProvider.productRepository
                    return CartViewModel(cartRepository, productRepository) as T
                }
            }
    }
}
