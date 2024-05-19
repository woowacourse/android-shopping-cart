package woowacourse.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.productDetail.ProductDetailViewModel
import woowacourse.shopping.repository.ShoppingCartItemRepository
import woowacourse.shopping.repository.ShoppingProductsRepository

class ProductDetailViewModelFactory(
    private val productId: Int,
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private val shoppingCartItemRepository: ShoppingCartItemRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(productId, shoppingProductsRepository, shoppingCartItemRepository) as T
        }
        throw IllegalArgumentException("this is known viewmodel class")
    }
}
