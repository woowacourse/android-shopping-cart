package woowacourse.shopping.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.OrderDao
import woowacourse.shopping.model.data.ProductDao

class CartViewModelFactory(
    private val productDao: ProductDao,
    private val orderDao: OrderDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(productDao, orderDao) as T
    }
}
