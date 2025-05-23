package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.providers.RepositoryProvider

class ProductDetailViewModel(
    private val productId: Long,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    lateinit var product: Product
        private set
    private val _eventAddedCartToast = MutableLiveData<Unit>()
    val eventAddedCartToast: LiveData<Unit> = _eventAddedCartToast

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity
    val totalPrice: LiveData<Int> get() = _quantity.map { quantity -> quantity * product.price }

    init {
        productRepository.fetchById(productId) { fetchedProduct ->
            product = fetchedProduct
        }
    }

    fun addCart() {
        cartRepository.insertOrAddQuantity(productId, quantity = quantity.value ?: 0) { result ->
            when {
                result.isSuccess -> {
                    _eventAddedCartToast.postValue(Unit)
                }

                result.isFailure -> {}   // DB 접근 에러 발생 / 토스트로 이유 알려주기
            }
        }
    }

    fun increaseQuantity() {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun decreaseQuantity() {
        _quantity.value = _quantity.value?.minus(1)?.coerceAtLeast(1)
    }

    companion object {
        private const val EXIST_PRODUCT_IN_CART = -1L

        fun createFactory(productId: Long): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(
                        productId = productId,
                        productRepository = RepositoryProvider.provideProductRepository(),
                        cartRepository = RepositoryProvider.provideCartRepository(),
                    ) as T
                }
            }
        }
    }
}
