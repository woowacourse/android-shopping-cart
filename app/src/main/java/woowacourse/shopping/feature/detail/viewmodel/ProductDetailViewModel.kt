package woowacourse.shopping.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _isSuccessAddToCart = MutableLiveData(false)
    val isSuccessAddToCart: LiveData<Boolean> get() = _isSuccessAddToCart

    private val _quantity = MutableLiveData<Quantity>()
    val quantity: LiveData<Quantity> get() = _quantity

    fun loadProduct(productId: Long) {
        _product.value = productRepository.find(productId)
        loadQuantity(productId)
    }

    fun addProductToCart(productId: Long) {
        val quantity = quantity.value ?: return
        cartRepository.addCartItem(productId, quantity.count)
        _isSuccessAddToCart.value = true
    }

    fun increaseQuantity() {
        val quantity = quantity.value ?: return
        _quantity.value = quantity.inc()
    }

    fun decreaseQuantity() {
        val quantity = quantity.value ?: return
        _quantity.value = quantity.dec()
    }

    private fun loadQuantity(productId: Long) {
        val cartItem = cartRepository.find(productId)
        if (cartItem == null) {
            _quantity.value = Quantity(1)
            return
        }
        _quantity.value = Quantity(cartItem.quantity.count)
    }
}
