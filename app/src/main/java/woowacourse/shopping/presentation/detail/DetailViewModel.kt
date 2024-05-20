package woowacourse.shopping.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _productInformation: MutableLiveData<Product> = MutableLiveData()
    val productInformation: LiveData<Product>
        get() = _productInformation

    private val _addComplete: MutableLiveData<Boolean> = MutableLiveData()
    val addComplete: LiveData<Boolean>
        get() = _addComplete

    fun loadProductInformation(id: Long) {
        _productInformation.value = productRepository.fetchProduct(id)
    }

    fun addToCart(id: Long) {
        cartRepository.addCartItem(productId = id, quantity = 1)
        _addComplete.value = true
    }
}
