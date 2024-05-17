package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            ProductDetailViewModel(
                extras.createSavedStateHandle(),
                productRepository,
                shoppingCartRepository,
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
