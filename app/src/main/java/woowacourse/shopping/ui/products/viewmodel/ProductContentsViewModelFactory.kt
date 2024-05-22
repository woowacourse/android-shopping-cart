package woowacourse.shopping.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductWithQuantityDao

class ProductContentsViewModelFactory(
    private val productWithQuantityDao: ProductWithQuantityDao,
    private val cartDao: CartDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductContentsViewModel(productWithQuantityDao, cartDao) as T
    }
}
