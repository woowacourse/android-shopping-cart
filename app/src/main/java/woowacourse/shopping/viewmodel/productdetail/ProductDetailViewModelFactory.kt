package woowacourse.shopping.viewmodel.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.repository.ShoppingCartRepository

class ProductDetailViewModelFactory(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(shoppingCartRepository) as T
        }
        return super.create(modelClass)
    }
}
