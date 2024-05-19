package woowacourse.shopping.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao

class ProductContentsViewModelFactory(
    private val productDao: ProductDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productDao) as T
    }
}
