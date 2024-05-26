package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider

class ShoppingCartViewModel(
    private val orderRepository: OrderRepository,
) : BaseViewModel(), ShoppingCartActionHandler {
    private val _uiState: MutableLiveData<ShoppingCartUiState> =
        MutableLiveData(ShoppingCartUiState())
    val uiState: LiveData<ShoppingCartUiState> get() = _uiState

    init {
        getPagingOrder(INIT_ID)
    }

    private fun getPagingOrder(
        lastSeenId: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        orderRepository.getPagingOrder(lastSeenId, pageSize).onSuccess { pagingOrder ->
            _uiState.value =
                _uiState.value?.copy(
                    pagingOrder = pagingOrder,
                )
        }.onFailure { _ ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    private fun getPagingOrderReversed(
        lastSeenId: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        orderRepository.getPagingOrderReversed(lastSeenId, pageSize).onSuccess { pagingOrder ->
            _uiState.value =
                _uiState.value?.copy(
                    pagingOrder = pagingOrder,
                )
        }.onFailure { _ ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun onClickClose(orderId: Int) {
        orderRepository.removeOrder(orderId)
        refreshPage()
    }

    override fun onClickPlusOrderButton(orderId: Int) {
        orderRepository.plusOrder(orderId)
        refreshPage()
    }

    override fun onClickMinusOrderButton(orderId: Int) {
        orderRepository.minusOrder(orderId)
        refreshPage()
    }

    private fun refreshPage() {
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                val id = pagingOrder.orderList.first().id - 1
                getPagingOrder(id)
            }
        }
    }

    fun onClickNextPage() {
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                val id = pagingOrder.orderList.last().id
                getPagingOrder(id)
            }
        }
        _uiState.value =
            _uiState.value?.copy(
                currentPage = _uiState.value?.currentPage?.plus(1) ?: 1,
            )
    }

    fun onClickPrePage() {
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                val id = pagingOrder.orderList.first().id
                getPagingOrderReversed(id)
            }
        }
        _uiState.value =
            _uiState.value?.copy(
                currentPage = _uiState.value?.currentPage?.minus(1) ?: 1,
            )
    }

    companion object {
        const val INIT_ID = 0
        const val PAGE_SIZE = 5
    }
}
