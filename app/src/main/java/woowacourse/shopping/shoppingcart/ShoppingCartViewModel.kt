package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingCartRepository
import woowacourse.shopping.uimodel.CartItemUiModel
import woowacourse.shopping.uimodel.toCartItemUiModel
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _cartItemUiModels: MutableLiveData<List<CartItemUiModel>> = MutableLiveData()
    val cartItemUiModels: LiveData<List<CartItemUiModel>> get() = _cartItemUiModels

    private val totalSize = MutableLiveData(DEFAULT_CART_ITEMS_SIZE)

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    val isLeftBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsLeftBtnEnable() }
        }

    val isRightBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsRightBtnEnable() }
            addSource(totalSize) { value = checkIsRightBtnEnable() }
        }

    private fun checkIsLeftBtnEnable() = _currentPage.value?.equals(DEFAULT_CURRENT_PAGE)?.not() ?: false

    private fun checkIsRightBtnEnable() = (totalSize.value != DEFAULT_CART_ITEMS_SIZE) && (_currentPage.value?.equals(totalSize.value))?.not() ?: false

    fun updatePageSize() {
        val totalItemSize = repository.shoppingCartSize()
        totalSize.value = ceil(totalItemSize / PAGE_SIZE.toDouble()).toInt()
    }

    fun loadCartItems(currentPage: Int) {
        runCatching {
            repository.shoppingCartItems(currentPage - DEFAULT_CURRENT_PAGE, PAGE_SIZE)
        }.onSuccess { shoppingCartItems ->
            _cartItemUiModels.value = shoppingCartItems.map { it.toCartItemUiModel() }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun deleteCartItem(productId: Long) {
        runCatching {
            repository.deleteShoppingCartItem(productId)
        }.onSuccess {
            val currentPage = requireNotNull(_currentPage.value)
            loadCartItems(currentPage)
        }
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value?.inc()
    }

    fun previousPage() {
        _currentPage.value = _currentPage.value?.dec()
    }

    companion object {
        private const val DEFAULT_CART_ITEMS_SIZE = 0
        private const val PAGE_SIZE = 5
        private const val DEFAULT_CURRENT_PAGE = 1
    }
}
