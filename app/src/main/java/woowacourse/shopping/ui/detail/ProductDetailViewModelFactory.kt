package woowacourse.shopping.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao

class ProductDetailViewModelFactory(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productDao, cartDao, applicationContext) as T
    }
}
