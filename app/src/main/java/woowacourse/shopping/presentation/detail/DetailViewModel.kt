package woowacourse.shopping.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.util.Event
import woowacourse.shopping.presentation.util.StringResource

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    id: Long,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _productInformation: MutableLiveData<Product> = MutableLiveData()
    val productInformation: LiveData<Product>
        get() = _productInformation

    private val _message: MutableLiveData<Event<StringResource>> = MutableLiveData()
    val message: LiveData<Event<StringResource>>
        get() = _message

    init {
        val productId: Long? = savedStateHandle.get<Long>(KEY_PRODUCT_ID)
        if (productId == null) {
            savedStateHandle[KEY_PRODUCT_ID] = id
            loadProductInformation(id)
        } else {
            loadProductInformation(productId)
        }
    }

    fun addToCart(id: Long) {
        cartRepository.addCartItem(productId = id, quantity = 1)
        _message.value = Event(StringResource(R.string.message_add_to_cart_complete))
    }

    private fun loadProductInformation(id: Long) {
        _productInformation.value = productRepository.fetchProduct(id)
    }

    companion object {
        private const val KEY_PRODUCT_ID = "key_product_id"
    }
}
