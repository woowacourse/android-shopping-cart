package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.ui.viewmodel.ProductViewModel

object ViewModelFactory : ViewModelProvider.Factory {
    fun createProductViewModelFactory(cartRepository: CartRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(cartRepository) as T
            }
        }
    }
}
