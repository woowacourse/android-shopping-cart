package woowacourse.shopping.view.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.cart.LocalCartProductRepository

class ShoppingCartViewModelFactory(
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            val database = ShoppingCartDatabase.getDataBase(applicationContext)
            val repository = LocalCartProductRepository(database.cartProductDao)
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}
