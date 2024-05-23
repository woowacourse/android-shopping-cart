package woowacourse.shopping.ui.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao

class CartViewModelFactory(
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(productDao, applicationContext) as T
    }
}
