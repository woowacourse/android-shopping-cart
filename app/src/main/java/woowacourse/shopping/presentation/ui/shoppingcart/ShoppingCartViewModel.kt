package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) :
    ViewModel(),
    ShoppingCartActionHandler {
    private val _orderList: MutableLiveData<List<Order>> = MutableLiveData(emptyList())
    val orderList: LiveData<List<Order>> = _orderList

    init {
        getOrderList()
    }

    private fun getOrderList() {
        repository.getOrderList().onSuccess { orderList ->
            _orderList.value = orderList
        }.onFailure {
            // TODO 예외 처리 예정
        }
    }

    override fun onClickClose(orderId: Int) {
        repository.removeOrder(orderId)
        getOrderList()
    }
}
