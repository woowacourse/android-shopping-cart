package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    ViewModel(),
    ShoppingCartActionHandler {
    private val _pagingOrder: MutableLiveData<PagingOrder> =
        MutableLiveData(PagingOrder(INIT_PAGE, emptyList(), false))
    val pagingOrder: LiveData<PagingOrder> get() = _pagingOrder

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> get() = _message

    init {
        getPagingOrder(INIT_PAGE)
    }

    private fun getPagingOrder(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        repository.getPagingOrder(page, pageSize).onSuccess { pagingOrder ->
            _pagingOrder.value = pagingOrder
        }.onFailure { e ->
            _message.value = e.message
        }
    }

    override fun onClickClose(orderId: Int) {
        repository.removeOrder(orderId)
        pagingOrder.value?.let { pagingOrder ->
            getPagingOrder(pagingOrder.currentPage)
        }
    }

    fun onClickNextPage() {
        pagingOrder.value?.let { pagingOrder ->
            getPagingOrder(pagingOrder.currentPage + 1)
        }
    }

    fun onClickPrePage() {
        pagingOrder.value?.let { pagingOrder ->
            getPagingOrder(pagingOrder.currentPage - 1)
        }
    }

    companion object {
        const val INIT_PAGE = 0
        const val PAGE_SIZE = 5
    }
}
