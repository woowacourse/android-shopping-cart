package woowacourse.shopping.view.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.util.SingleLiveData

class ProductDetailViewModel(
    val product: Product,
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _navigateEvent = SingleLiveData<Unit>()
    val navigateEvent: SingleLiveData<Unit> = _navigateEvent

    private val _errorEvent = SingleLiveData<Unit>()
    val errorEvent: SingleLiveData<Unit> = _errorEvent

    fun addToShoppingCart() {
        runCatching {
            repository.insert(product.id)
        }.onSuccess {
            _navigateEvent.value = Unit
        }.onFailure {
            _errorEvent.value = Unit
        }
    }

    companion object {
        fun provideFactory(
            product: Product,
            repository: ShoppingCartRepository = ShoppingProvider.shoppingCartRepository,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                        return ProductDetailViewModel(product, repository) as T
                    }
                    throw IllegalArgumentException()
                }
            }
    }
}
