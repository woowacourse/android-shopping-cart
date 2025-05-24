package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.utils.SingleLiveData

class ProductDetailViewModel(
    val product: Product,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _navigateEvent = SingleLiveData<Unit>()
    val navigateEvent: SingleLiveData<Unit> = _navigateEvent

    private val _errorEvent = SingleLiveData<Unit>()
    val errorEvent: SingleLiveData<Unit> = _errorEvent

    private val _quantity = MutableLiveData(0)
    val quantity: LiveData<Int> = _quantity

    fun addToShoppingCart() {
        runCatching {
            shoppingCartRepository.insert(product.id, _quantity.value ?: 0)
        }.onSuccess {
            _navigateEvent.value = Unit
        }.onFailure {
            _errorEvent.value = Unit
        }
    }

    fun increaseItemQuantity(productId: Long) {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun decreaseItemQuantity(productId: Long) {
        _quantity.value = _quantity.value?.minus(1)
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
