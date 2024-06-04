package woowacourse.shopping.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository

class CartViewModelFactory(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            CartViewModel(cartRepository, productRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
