package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingCartRepositoryInterface
import woowacourse.shopping.ViewModelQuantityActions
import woowacourse.shopping.uimodel.CartItemUiModel
import woowacourse.shopping.uimodel.toCartItemUiModel
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepositoryInterface,
) : ViewModel(), ViewModelQuantityActions {
    private val _cartItemUiModels: MutableLiveData<List<CartItemUiModel>> = MutableLiveData()
    val cartItemUiModels: LiveData<List<CartItemUiModel>> get() = _cartItemUiModels

    private val totalSize = MutableLiveData(DEFAULT_CART_ITEMS_SIZE)

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _changedItems: MutableLiveData<Set<Long>> = MutableLiveData()
    val changedItems: LiveData<Set<Long>> get() = _changedItems

    val isLeftBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsLeftBtnEnable() }
        }

    val isRightBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsRightBtnEnable() }
            addSource(totalSize) { value = checkIsRightBtnEnable() }
        }

    private fun checkIsLeftBtnEnable() =
        _currentPage.value?.equals(DEFAULT_CURRENT_PAGE)?.not() ?: false

    private fun checkIsRightBtnEnable() =
        (totalSize.value != DEFAULT_CART_ITEMS_SIZE) && (_currentPage.value?.equals(totalSize.value))?.not() ?: false

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
            putChangedItem(productId)
        }
    }

    override fun plusQuantity(productId: Long) {
        runCatching {
            repository.plusCartItemQuantity(productId)
        }.onSuccess { updatedItem ->
            _cartItemUiModels.value = cartItemUiModels.value?.map {
                if (it.id == productId) {
                    it.copy(
                        quantity = updatedItem.quantity,
                        totalPrice = updatedItem.totalPrice.value,
                    )
                } else {
                    it
                }
            }
            putChangedItem(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    override fun minusQuantity(productId: Long) {
        runCatching {
            repository.minusCartItemQuantity(productId)
        }.onSuccess { updatedItem ->
            _cartItemUiModels.value = cartItemUiModels.value?.map {
                if (it.id == productId) {
                    it.copy(
                        quantity = updatedItem.quantity,
                        totalPrice = updatedItem.totalPrice.value,
                    )
                } else {
                    it
                }
            }
            putChangedItem(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun putChangedItem(updatedItem: Long) {
        _changedItems.value = changedItems.value.orEmpty() + updatedItem
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
