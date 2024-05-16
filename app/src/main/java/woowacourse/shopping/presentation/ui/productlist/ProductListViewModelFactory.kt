package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.ProductListRepository

class ProductListViewModelFactory(
    private val repository: ProductListRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            ProductListViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
