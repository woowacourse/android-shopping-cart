package woowacourse.shopping.shoppingcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.ShoppingRepository
import woowacourse.shopping.domain.QuantityUpdate
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

    private fun currentCartItems(): CartItemUiModels = _loadState?.value?.currentCartItems ?: error("초기화 이후에 메서드를 실행해주세요")

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
                LoadCartItemState.InitView(
                    shoppingCartItems.map { it.toShoppingCartUiModel() }
                        .let(::CartItemUiModels),
                )
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
            _loadState.value =
                LoadCartItemState.DeleteCartItem(
                    productId,
                    currentCartItems().deleteCartItem(productId),
                )
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
            val cartItem = shoppingCartItem.toShoppingCartUiModel()
            _loadState.value =
                LoadCartItemState.AddNextPageOfItem(
                    cartItem,
                    currentCartItems().updateCartItem(cartItem),
                )
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
                    _loadState.value =
                        LoadCartItemState.ChangeItemCount(
                            currentCartItems().updateCartItem(updatedUiModel),
                            updatedUiModel,
                        )
                }

                QuantityUpdate.Failure -> {
                    error(" 장바구니 수량 변경에 실패했습니다. ")
                }
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
                    _loadState.value =
                        LoadCartItemState.ChangeItemCount(
                            currentCartItems().updateCartItem(updatedUiModel),
                            updatedUiModel,
                        )
                }

                QuantityUpdate.Failure -> LoadCartItemState.MinusFail(currentCartItems())
            }
        }.onFailure {
            Log.d(this::class.java.simpleName, "$it")
        }
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
