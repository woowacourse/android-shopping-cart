package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ProductDetailViewModelFactory(
    private val productListRepository: ProductListRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            ProductDetailViewModel(
                extras.createSavedStateHandle(),
                productListRepository,
                shoppingCartRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
