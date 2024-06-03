package woowacourse.shopping.presentation.ui.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.OrderRepository
import java.lang.IllegalArgumentException

class ShoppingCartViewModelFactory(
    private val orderRepository: OrderRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(
                orderRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
