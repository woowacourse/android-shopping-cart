package woowacourse.shopping.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.util.Event

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    productId: Long,
) : ViewModel() {
    private val _productInformation: MutableLiveData<Product> = MutableLiveData()
    val productInformation: LiveData<Product>
        get() = _productInformation

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int>
        get() = _quantity

    private val _addComplete = MutableLiveData<Event<Boolean>>()
    val addComplete: LiveData<Event<Boolean>>
        get() = _addComplete

    init {
        loadProductInformation(productId)
        loadCartItem(productId)
    }

    fun loadCartItem(id: Long) {
        _quantity.value = cartRepository.fetchCartItem(id)?.quantity ?: 1
    }

    fun addQuantity() {
        _quantity.value = quantity.value?.plus(1)
    }

    fun minusQuantity() {
        if (quantity.value!! > 1) {
            _quantity.value = quantity.value?.minus(1)
        }
    }

    fun loadProductInformation(id: Long) {
        _productInformation.value = productRepository.fetchProduct(id)
    }

    fun addToCart(
        id: Long,
        quantity: Int,
    ) {
        cartRepository.addCartItem(productId = id, quantity = quantity)
        _addComplete.value = Event(true)
    }
}
