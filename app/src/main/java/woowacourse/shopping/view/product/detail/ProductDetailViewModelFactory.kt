package woowacourse.shopping.view.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModelFactory(
    private val product: Product,
    private val cartProductRepository: CartProductRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(product, cartProductRepository) as T
        }
        throw IllegalArgumentException()
    }
}
