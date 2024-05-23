package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.productlist.toProductUiModel
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val totalPage = MutableLiveData(NO_CART_ITEM)

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

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
            _loadState.value =
                LoadCartItemState.InitView(shoppingCartItems.map { it.product.toProductUiModel(1) })
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun deleteCartItem(productId: Long) {
        runCatching {
            repository.deleteShoppingCartItem(productId)
        }.onSuccess {
            if (_currentPage.value != totalPage.value && totalPage.value != 0) {
                loadCartItemOfNextPage()
            }
            updatePageCount()
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun loadCartItemOfNextPage() {
        runCatching {
            val currentPage = _currentPage?.value ?: DEFAULT_CURRENT_PAGE
            repository.shoppingCartItemByPosition(currentPage - 1, PAGE_SIZE, MAX_POSITION_OF_ITEM)
        }.onSuccess { shoppingCartItem ->
            _loadState.value =
                LoadCartItemState.AddNextPageOfItem(shoppingCartItem.product.toProductUiModel(1))
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
