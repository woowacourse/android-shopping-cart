package woowacourse.shopping.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _productLoadError = MutableLiveData<Boolean>(false)
    val productLoadError: LiveData<Boolean> get() = _productLoadError

    fun loadProduct() {
        runCatching {
            productRepository.find(productId)
        }.onSuccess {
            _product.value = it
            _productLoadError.value = false
        }.onFailure {
            _productLoadError.value = true
        }
    }

    fun addCartProduct() {
        cartRepository.increaseQuantity(_product.value ?: return)
    }
}
