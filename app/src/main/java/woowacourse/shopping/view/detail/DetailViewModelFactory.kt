package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository

class DetailViewModelFactory(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository,
    private val productId: Long,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(cartRepository = cartRepository, shoppingRepository = shoppingRepository, productId = productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
