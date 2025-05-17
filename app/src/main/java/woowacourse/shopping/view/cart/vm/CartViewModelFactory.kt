package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModelFactory : ViewModelProvider.Factory {
    private val cartRepository: CartRepository = CartRepositoryImpl(CartStorage)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException()
    }
}
