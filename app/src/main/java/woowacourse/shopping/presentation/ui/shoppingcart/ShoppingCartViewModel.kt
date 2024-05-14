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
        MutableLiveData(PagingOrder(1, emptyList(), false))
    val pagingOrder: LiveData<PagingOrder> = _pagingOrder

    init {
        getOrderList(0)
    }

    private fun getOrderList(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ) {
        repository.getOrderList(page, pageSize).onSuccess { pagingOrder ->
            _pagingOrder.value = pagingOrder
        }.onFailure {
            // TODO 예외 처리 예정
        }
    }

    override fun onClickClose(orderId: Int) {
        repository.removeOrder(orderId)
        pagingOrder.value?.let { pagingOrder ->
            getOrderList(pagingOrder.currentPage)
        }
    }

    fun onClickNextPage() {
        pagingOrder.value?.let { pagingOrder ->
            if (pagingOrder.last) return
            getOrderList(pagingOrder.currentPage + 1)
        }
    }

    fun onClickPrePage() {
        pagingOrder.value?.let { pagingOrder ->
            getOrderList(pagingOrder.currentPage - 1)
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
