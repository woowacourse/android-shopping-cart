package woowacourse.shopping.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.model.data.ProductWithQuantityDao

class ProductDetailViewModelFactory(
    private val productWithQuantityDao: ProductWithQuantityDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(productWithQuantityDao) as T
    }
}
