package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.model.db.cart.CartRepository

class CartViewModelFactory(
    private val productDao: ProductDao,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(productDao, cartRepository) as T
    }
}
