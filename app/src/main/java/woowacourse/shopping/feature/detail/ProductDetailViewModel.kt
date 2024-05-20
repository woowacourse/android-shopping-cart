package woowacourse.shopping.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.common.Event
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class ProductDetailViewModel(
    private val productId: Long,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _productLoadError = MutableLiveData<Event<Boolean>>()
    val productLoadError: LiveData<Event<Boolean>> get() = _productLoadError

    private val _isSuccessAddCart = MutableLiveData<Event<Boolean>>()
    val isSuccessAddCart: LiveData<Event<Boolean>> get() = _isSuccessAddCart

    init {
        loadProduct()
    }

    private fun loadProduct() {
        runCatching {
            productRepository.find(productId)
        }.onSuccess {
            _product.value = it
            _productLoadError.value = Event(false)
        }.onFailure {
            _productLoadError.value = Event(true)
        }
    }

    fun addCartProduct() {
        runCatching {
            cartRepository.increaseQuantity(_product.value ?: return)
        }.onSuccess {
            _isSuccessAddCart.value = Event(true)
        }.onFailure {
            _isSuccessAddCart.value = Event(false)
        }
    }
}
