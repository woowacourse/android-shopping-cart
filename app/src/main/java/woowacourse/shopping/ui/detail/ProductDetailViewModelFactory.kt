package woowacourse.shopping.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao

class ProductDetailViewModelFactory(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productDao, cartDao) as T
    }
}
