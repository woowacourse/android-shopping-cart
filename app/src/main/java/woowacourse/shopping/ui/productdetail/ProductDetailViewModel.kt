package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.product.ProductOverViewRepository
import woowacourse.shopping.providers.RepositoryProvider

class ProductDetailViewModel(
    private val productId: Long,
    private val repository: ProductOverViewRepository,
) : ViewModel() {
    private val _eventAddedCartToast = MutableLiveData<Unit>()
    val eventAddedCartToast: LiveData<Unit> = _eventAddedCartToast

    private var _cartProductUiState: MutableLiveData<CartProduct?> = MutableLiveData()
    val cartProductUiState: LiveData<CartProduct?> get() = _cartProductUiState

    init {
        repository.findById(productId) { result ->
            result.onSuccess { product ->
                product ?: return@onSuccess // 존재 하지 않는 상품을 조회했음을 알려야 함
                _cartProductUiState.postValue(CartProduct(product = product, _quantity = 1))
            }
        }
    }

    fun addCart() {
        val cartProduct = cartProductUiState.value ?: return
        repository.insertOrAddQuantity(cartProduct.product.id!!, cartProduct.quantity) { result ->
            result.onSuccess {
                _eventAddedCartToast.postValue(Unit)
            }
        }
    }

    fun increaseQuantity() {
        val cartProduct = cartProductUiState.value ?: return
        _cartProductUiState.postValue(cartProduct.increase())
    }

    fun decreaseQuantity() {
        val cartProduct = cartProductUiState.value ?: return
        val decreasedQuantityProduct = cartProduct.decrease()
        if (decreasedQuantityProduct.quantity != 0) {
            _cartProductUiState.postValue(
                decreasedQuantityProduct,
            )
        }
    }

    companion object {
        fun createFactory(productId: Long): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(
                        productId = productId,
                        repository = RepositoryProvider.provideProductOverViewRepository(),
                    ) as T
                }
            }
        }
    }
}
