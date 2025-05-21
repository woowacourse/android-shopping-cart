package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.CartStorageImpl

class CartViewModelFactory(private val database: ShoppingDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val storage: CartStorage = CartStorageImpl(database.productDao())

        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(storage) as T
        }
        throw IllegalArgumentException()
    }
}
