package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.QuantityUpdate
import woowacourse.shopping.repository.ShoppingRepository
import woowacourse.shopping.shoppingcart.uimodel.CartItemState
import woowacourse.shopping.shoppingcart.uimodel.CartItemUiModel
import woowacourse.shopping.shoppingcart.uimodel.CountChangeEvent
import woowacourse.shopping.util.MutableSingleLiveData
import woowacourse.shopping.util.SingleLiveData
import kotlin.math.ceil

class ShoppingCartViewModel(
    private val repository: ShoppingRepository,
) : ViewModel() {
    private val totalPage = MutableLiveData(NO_CART_ITEM)

    private val _currentPage: MutableLiveData<Int> = MutableLiveData<Int>(DEFAULT_CURRENT_PAGE)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _cartItemChange: MutableSingleLiveData<CountChangeEvent> = MutableSingleLiveData()
    val cartItemChange: SingleLiveData<CountChangeEvent> get() = _cartItemChange

    private val _cartItemState: MutableLiveData<CartItemState> =
        MutableLiveData()
    val cartItemState: LiveData<CartItemState> get() = _cartItemState

    val isLeftBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsLeftBtnEnable() }
        }

    val isRightBtnEnable =
        MediatorLiveData(false).apply {
            addSource(_currentPage) { value = checkIsRightBtnEnable() }
            addSource(totalPage) { value = checkIsRightBtnEnable() }
        }

    private val _changedProductIds: MutableSet<Long> = mutableSetOf()
    val changedProductIds: Set<Long>
        get() = _changedProductIds.toSet()

    private fun currentCartItems(): List<CartItemUiModel> = _cartItemState?.value?.currentCartItems ?: error("초기화 이후에 메서드를 실행해주세요")

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
            _cartItemState.value = CartItemState.Init(shoppingCartItems.map { it.toShoppingCartUiModel() })
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun deleteCartItem(productId: Long) {
        runCatching {
            repository.deleteShoppingCartItem(productId)
        }.onSuccess {
            if (checkIsRightBtnEnable()) {
                loadCartItemOfNextPage()
            }
            _cartItemState.value = CartItemState.Change(currentCartItems().deleteCartItem(productId))
            _cartItemChange.setValue(CountChangeEvent.DeleteCartItem(productId))
            updatePageCount()
            _changedProductIds.add(productId)
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    private fun loadCartItemOfNextPage() {
        runCatching {
            val currentPage = _currentPage?.value ?: DEFAULT_CURRENT_PAGE
            repository.shoppingCartItemByPosition(currentPage - 1, PAGE_SIZE, MAX_POSITION_OF_ITEM)
        }.onSuccess { shoppingCartItem ->
            val cartItem = shoppingCartItem.toShoppingCartUiModel()
            _cartItemState.value = CartItemState.Change(currentCartItems() + shoppingCartItem.toShoppingCartUiModel())
            _cartItemChange.setValue(CountChangeEvent.AddNextPageOfItem(cartItem))
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value?.inc()
        loadCartItems()
    }

    fun plusCartItemCount(productId: Long) {
        runCatching {
            repository.increasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> {
                    val updatedUiModel = result.value.toShoppingCartUiModel()
                    _cartItemState.value = CartItemState.Change(currentCartItems().updateCartItem(updatedUiModel))
                    _cartItemChange.setValue(CountChangeEvent.ChangeItemCount(updatedUiModel))
                    _changedProductIds.add(productId)
                }

                QuantityUpdate.CantChange -> _cartItemChange.setValue(CountChangeEvent.PlusChangeFail)
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun minusCartItemCount(productId: Long) {
        runCatching {
            repository.decreasedCartItem(productId)
        }.onSuccess { result ->
            when (result) {
                is QuantityUpdate.Success -> {
                    val updatedUiModel = result.value.toShoppingCartUiModel()
                    _cartItemState.value = CartItemState.Change(currentCartItems().updateCartItem(updatedUiModel))
                    _cartItemChange.setValue(CountChangeEvent.ChangeItemCount(updatedUiModel))
                    _changedProductIds.add(productId)
                }

                QuantityUpdate.CantChange -> _cartItemChange.setValue(CountChangeEvent.PlusChangeFail)
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
    }

    fun previousPage() {
        _currentPage.value = _currentPage.value?.dec()
        loadCartItems()
    }

    private fun List<CartItemUiModel>.updateCartItem(cartItemUiModel: CartItemUiModel): List<CartItemUiModel> =
        currentCartItems().map { if (it.id == cartItemUiModel.id) cartItemUiModel else it }

    private fun List<CartItemUiModel>.deleteCartItem(productId: Long): List<CartItemUiModel> =
        currentCartItems().filterNot { it.id == productId }

    companion object {
        private const val PAGE_SIZE = 5
        private const val DEFAULT_CURRENT_PAGE = 1
        private const val NO_CART_ITEM = 0
        private const val MAX_POSITION_OF_ITEM = 4
    }
}
