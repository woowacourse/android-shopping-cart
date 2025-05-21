package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.data.CartStorageImpl

class CartViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val storage: CartStorage = CartStorageImpl

        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(storage) as T
        }
        throw IllegalArgumentException()
    }
}
