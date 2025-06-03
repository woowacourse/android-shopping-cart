package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.max

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CartItem>>(emptyList())
    val products: LiveData<List<CartItem>> = _products

    private val _page = MutableLiveData(DEFAULT_PAGE)
    val page: LiveData<Int> = _page

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val limit = 5

    init {
        fetchShoppingCart(isNextPage = false)
    }

    fun fetchShoppingCart(
        isNextPage: Boolean,
        isRefresh: Boolean = false,
    ) {
        val currentPage = _page.value ?: DEFAULT_PAGE
        val newPage = calculatePage(isNextPage, currentPage, isRefresh)
        val newOffset = (newPage - DEFAULT_PAGE) * limit

        cartRepository.getCartItems(limit = limit, offset = newOffset) { items, hasMore ->
            _products.postValue(items.toList())
            _page.postValue(newPage)
            _hasMore.postValue(hasMore)
        }
    }

    fun deleteProduct(cartItem: CartItem) {
        cartRepository.deleteCartItem(cartItem.product.id) {
            val updatedList =
                _products.value.orEmpty().filterNot {
                    it.product.id == cartItem.product.id
                }
            _products.postValue(updatedList)
        }
    }

    fun increaseAmount(productId: Long) {
        cartRepository.increaseCartItem(productId) { updatedItem ->
            updatedItem?.let {
                val updatedList =
                    _products.value.orEmpty().map {
                        if (it.product.id == productId) updatedItem else it
                    }
                _products.postValue(updatedList)
            }
        }
    }

    fun decreaseAmount(productId: Long) {
        cartRepository.decreaseCartItem(productId) { updatedItem ->
            val currentList = _products.value.orEmpty()
            val newList =
                if (updatedItem == null) {
                    currentList.filterNot { it.product.id == productId }
                } else {
                    currentList.map {
                        if (it.product.id == productId) updatedItem else it
                    }
                }
            _products.postValue(newList)
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
            max(DEFAULT_PAGE, currentPage - DEFAULT_PAGE)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 1

        fun factory(cartRepository: CartRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T = CartViewModel(cartRepository) as T
            }
    }
}
