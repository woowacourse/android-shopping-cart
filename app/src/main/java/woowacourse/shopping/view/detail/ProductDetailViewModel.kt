package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.domain.CartItem

class ProductDetailViewModel(
    private val repository: ShoppingCartRepository2,
) : ViewModel() {
    fun addProduct(cartItem: CartItem) {
        repository.insert(cartItem)
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun createFactory(repository: ShoppingCartRepository2): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (ProductDetailViewModel(repository) as T)
                }
            }
        }
    }
}
