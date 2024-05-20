package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.CART_ITEM_PAGE_SIZE
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.cart.model.ShoppingCart

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    var shoppingCart = ShoppingCart()
    private val totalItemSize: Int get() = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE

    private val _currentPage: MutableLiveData<Int> =
        MutableLiveData(MIN_PAGE_COUNT)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _shoppingCartState: MutableLiveData<ShoppingCartState> =
        MutableLiveData()
    val shoppingCartState: LiveData<ShoppingCartState> get() = _shoppingCartState

    private val _errorState: MutableLiveData<ShoppingCartState.ErrorState> =
        MutableLiveData()
    val errorState: LiveData<ShoppingCartState.ErrorState> get() = _errorState

    fun deleteShoppingCartItem(itemId: Long) {
        try {
            shoppingCartRepository.deleteCartItem(itemId)
            shoppingCart.deleteProduct(itemId)
            _shoppingCartState.value = ShoppingCartState.DeleteShoppingCart.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorState.value = ShoppingCartState.DeleteShoppingCart.Fail
                else -> _errorState.value = ShoppingCartState.ErrorState.NotKnownError
            }
        }
    }

    fun loadPagingCartItemList(pagingSize: Int) {
        try {
            val itemSize = shoppingCart.cartItems.value?.size ?: DEFAULT_ITEM_SIZE
            val pagingData = shoppingCartRepository.loadPagingCartItems(itemSize, pagingSize)
            shoppingCart.addProducts(pagingData)
            _shoppingCartState.value = ShoppingCartState.LoadCartItemList.Success
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException -> _errorState.value = ShoppingCartState.LoadCartItemList.Fail
                else -> _errorState.value = ShoppingCartState.ErrorState.NotKnownError
            }
        }
    }

    fun getUpdatePageData(): List<CartItem> {
        val startIndex =
            ((currentPage.value ?: MIN_PAGE_COUNT) - MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE
        val endIndex = getLastItemIndex()
        return shoppingCart.cartItems.value?.subList(startIndex, endIndex)
            ?: emptyList()
    }

    fun hasLastItem(): Boolean {
        val endIndex = getLastItemIndex()
        return endIndex >= (totalItemSize)
    }

    fun isExistPrevPage(): Boolean {
        return (currentPage.value ?: MIN_PAGE_COUNT) > MIN_PAGE_COUNT
    }

    fun isExistNextPage(): Boolean {
        return (currentPage.value ?: MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE < totalItemSize
    }

    fun increaseCurrentPage() {
        _currentPage.value = _currentPage.value?.plus(INCREMENT_AMOUNT)
    }

    fun decreaseCurrentPage() {
        _currentPage.value = _currentPage.value?.minus(INCREMENT_AMOUNT)
    }

    private fun getLastItemIndex(): Int {
        return minOf(
            (currentPage.value ?: MIN_PAGE_COUNT) * CART_ITEM_PAGE_SIZE,
            totalItemSize,
        )
    }

    companion object {
        private const val MIN_PAGE_COUNT = 1
        private const val INCREMENT_AMOUNT = 1
    }
}
