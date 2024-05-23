package woowacourse.shopping.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.ShoppingDatabase

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = ShoppingDatabase.getInstance().cartProductDao()
        val cartRepository = CartRepository(dao)
        return MainViewModel(cartRepository = cartRepository) as T
    }
}
