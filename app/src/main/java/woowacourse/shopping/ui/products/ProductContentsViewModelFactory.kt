package woowacourse.shopping.ui.products

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductDao

class ProductContentsViewModelFactory(
    private val productDao: ProductDao,
    private val application: Context,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productDao, application) as T
    }
}
