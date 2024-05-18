package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.shoppingcart.adapter.ShoppingCartPagingSource

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    BaseViewModel(),
    ShoppingCartActionHandler {
    private val _uiState: MutableLiveData<ShoppingCartUiState> =
        MutableLiveData(ShoppingCartUiState())
    val uiState: LiveData<ShoppingCartUiState> get() = _uiState

    private val shoppingCartPagingSource = ShoppingCartPagingSource(repository)

    init {
        getPagingOrder(INIT_PAGE)
    }

    private fun getPagingOrder(page: Int) {
        shoppingCartPagingSource.load(page).onSuccess { pagingOrder ->
            _uiState.value =
                _uiState.value?.copy(pagingOrder = pagingOrder)
        }.onFailure { e ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun onClickClose(orderId: Int) {
        repository.removeOrder(orderId)
        uiState.value?.let { state ->
            getPagingOrder(state.pagingOrder.currentPage)
        }
    }

    fun onClickNextPage() {
        uiState.value?.let { state ->
            getPagingOrder(state.pagingOrder.currentPage + 1)
        }
    }

    fun onClickPrePage() {
        uiState.value?.let { state ->
            getPagingOrder(state.pagingOrder.currentPage - 1)
        }
    }

    companion object {
        const val INIT_PAGE = 0
        const val PAGE_SIZE = 5
    }
}
