package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : BaseViewModel(), ShoppingCartActionHandler {
    private val _uiState: MutableLiveData<ShoppingCartUiState> =
        MutableLiveData(ShoppingCartUiState())
    val uiState: LiveData<ShoppingCartUiState> get() = _uiState

    init {
        getPagingOrder(INIT_PAGE)
    }

    private fun getPagingOrder(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        shoppingCartRepository.getPagingOrder(page, pageSize).onSuccess { pagingOrder ->
            _uiState.value =
                _uiState.value?.copy(
                    pagingOrder = pagingOrder,
                )
        }.onFailure { _ ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun onClickClose(orderId: Int) {
        shoppingCartRepository.removeOrder(orderId)
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                getPagingOrder(pagingOrder.currentPage)
            }
        }
    }

    override fun onClickPlusOrderButton(orderId: Int) {
        shoppingCartRepository.plusOrder(orderId)
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                getPagingOrder(pagingOrder.currentPage)
            }
        }
    }

    override fun onClickMinusOrderButton(orderId: Int) {
        shoppingCartRepository.minusOrder(orderId)
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                getPagingOrder(pagingOrder.currentPage)
            }
        }
    }

    fun onClickNextPage() {
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                getPagingOrder(pagingOrder.currentPage + 1)
            }
        }
    }

    fun onClickPrePage() {
        uiState.value?.let { state ->
            state.pagingOrder?.let { pagingOrder ->
                getPagingOrder(pagingOrder.currentPage - 1)
            }
        }
    }

    companion object {
        const val INIT_PAGE = 0
        const val PAGE_SIZE = 5
    }
}
