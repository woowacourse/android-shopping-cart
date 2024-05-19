package woowacourse.shopping.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _isSuccessAddToCart = MutableLiveData(false)
    val isSuccessAddToCart: LiveData<Boolean> get() = _isSuccessAddToCart

    fun loadProduct(productId: Long) {
        _product.value = productRepository.find(productId)
    }

    fun addProductToCart() {
        val product = product.value ?: return
        cartRepository.increaseQuantity(product)
        _isSuccessAddToCart.value = true
    }
}
