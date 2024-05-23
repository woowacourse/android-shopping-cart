package woowacourse.shopping.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.home.QuantityListener
import woowacourse.shopping.presentation.util.Event
import woowacourse.shopping.presentation.util.StringResource
import kotlin.concurrent.thread

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    id: Long,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), QuantityListener {
    private val _productInformation: MutableLiveData<CartableProduct> = MutableLiveData()
    val productInformation: LiveData<CartableProduct>
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

    fun addToCart(productId: Long) {
        thread {
            val targetProduct = productRepository.fetchProduct(productId)
            if (productInformation.value?.quantity == 0 && targetProduct.cartItem?.id != null) {
                cartRepository.removeCartItem(targetProduct.cartItem)
            } else {
                if (targetProduct.cartItem?.id != null) {
                    cartRepository.updateQuantity(targetProduct.cartItem.id, productInformation.value?.quantity ?: return@thread)
                } else {
                    cartRepository.addCartItem(CartItem(productId = productId))
                }
            }
            _message.postValue(Event(StringResource(R.string.message_add_to_cart_complete)))
        }
    }

    override fun onQuantityChange(productId: Long, quantity: Int) {
        if (quantity < 0) return
        _productInformation.value = productInformation.value?.copy(
            cartItem = CartItem(productId = productId, quantity = quantity)
        )
    }

    private fun loadProductInformation(id: Long) {
        thread {
            _productInformation.postValue(productRepository.fetchProduct(id))
        }
    }

    companion object {
        private const val KEY_PRODUCT_ID = "key_product_id"
    }
}
