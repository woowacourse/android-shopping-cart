package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.providers.RepositoryProvider

class ProductDetailViewModel(
    val product: Product,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _eventAddedCartToast = MutableLiveData<Unit>()
    val eventAddedCartToast: LiveData<Unit> = _eventAddedCartToast

    fun addCart() {
        cartRepository.insert(product)
        _eventAddedCartToast.postValue(Unit)
    }

    companion object {
        fun createFactory(product: Product): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(
                        product,
                        RepositoryProvider.provideCartRepository()
                    ) as T
                }
            }
        }
    }
}
