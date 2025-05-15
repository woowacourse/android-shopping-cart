package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.ui.viewmodel.CartViewModel

object ViewModelFactory {
    fun createCartViewModelFactory(cartRepository: CartRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(cartRepository) as T
            }
        }
    }
}
