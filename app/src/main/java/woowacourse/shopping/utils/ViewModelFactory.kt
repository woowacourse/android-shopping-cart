package woowacourse.shopping.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.ui.viewmodel.CartViewModel
import woowacourse.shopping.ui.viewmodel.ProductListViewModel

object ViewModelFactory {
    fun createCartViewModelFactory(cartRepository: CartRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(cartRepository) as T
            }
        }
    }

    fun createProductViewModelFactory(productRepository: ProductRepository): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductListViewModel(productRepository) as T
            }
        }
    }
}
