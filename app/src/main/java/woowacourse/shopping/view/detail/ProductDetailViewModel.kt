package woowacourse.shopping.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(private val shoppingCartRepository: ShoppingCartRepository) : ViewModel() {
    fun addProduct(product: Product) {
        shoppingCartRepository.insert(product)
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun createFactory(shoppingCartRepository: ShoppingCartRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (ProductDetailViewModel(shoppingCartRepository) as T)
                }
            }
        }
    }
}
