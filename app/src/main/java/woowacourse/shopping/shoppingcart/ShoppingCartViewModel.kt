package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.productlist.toProductUiModel
import woowacourse.shopping.util.Event
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val _cartItems: MutableLiveData<List<ProductUiModel>> = MutableLiveData()
    val cartItems: LiveData<List<ProductUiModel>> get() = _cartItems

    private val totalPage = MutableLiveData(NO_CART_ITEM)

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _isDeleteSuccess: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isDeleteSuccess: LiveData<Event<Boolean>> get() = _isDeleteSuccess

    private val _loadState: MutableLiveData<LoadCartItemState> =
        MutableLiveData<LoadCartItemState>()
    val loadState: LiveData<LoadCartItemState> get() = _loadState

    val isLeftBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsLeftBtnEnable() }
        }

    val isRightBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsRightBtnEnable() }
            addSource(totalPage) { value = checkIsRightBtnEnable() }
        }

    private fun checkIsLeftBtnEnable() = _currentPage.value?.equals(DEFAULT_CURRENT_PAGE)?.not() ?: false

    private fun checkIsRightBtnEnable(): Boolean {
        val currentPage = _currentPage.value ?: return false
        val totalPage = totalPage.value ?: return false

        return (totalPage != NO_CART_ITEM) && (currentPage >= totalPage).not()
    }

    fun updatePageCount() {
        val totalItemSize = repository.shoppingCartSize()
        totalPage.value = ceil(totalItemSize / PAGE_SIZE.toDouble()).toInt()
    }

    fun loadCartItems() {
        runCatching {
            val currentPage = _currentPage?.value ?: DEFAULT_CURRENT_PAGE
            repository.shoppingCartItems(currentPage - DEFAULT_CURRENT_PAGE, PAGE_SIZE)
        }.onSuccess { shoppingCartItems ->
            _cartItems.value = shoppingCartItems.map { it.product.toProductUiModel() }
            _loadState.value = LoadCartItemState.InitView
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun deleteCartItem(productId: Long) {
        runCatching {
            repository.deleteShoppingCartItem(productId)
        }.onSuccess {
            updatePageCount()
            loadCartItems()
            _isDeleteSuccess.value = Event(true)
//            if (_currentPage.value != totalPage.value) {
//                loadCartItemOfNextPage()
//            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun loadCartItemOfNextPage() {
        runCatching {
            val currentPage = _currentPage?.value ?: DEFAULT_CURRENT_PAGE
            repository.shoppingCartItemByPosition(currentPage, PAGE_SIZE, MAX_POSITION_OF_ITEM)
        }.onSuccess { shoppingCartItem ->
            val previousCartItems = requireNotNull(_cartItems.value)
            _cartItems.value = previousCartItems + shoppingCartItem.product.toProductUiModel()
            _loadState.value = LoadCartItemState.AddNextPageOfItem
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value?.inc()
        loadCartItems()
    }

    fun previousPage() {
        _currentPage.value = _currentPage.value?.dec()
        loadCartItems()
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_CURRENT_PAGE = 1
        private const val NO_CART_ITEM = 0
        private const val MAX_POSITION_OF_ITEM = 4
    }
}
