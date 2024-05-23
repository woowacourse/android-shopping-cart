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

    fun addToCart(id: Long) {
        thread {
            cartRepository.addCartItem(CartItem(productId = id, quantity = 1))
            _message.postValue(Event(StringResource(R.string.message_add_to_cart_complete)))
        }
    }

    private fun loadProductInformation(id: Long) {
        thread {
            _productInformation.postValue(productRepository.fetchProduct(id))
        }
    }

    companion object {
        private const val KEY_PRODUCT_ID = "key_product_id"
    }

    override fun onQuantityChange(productId: Long, quantity: Int) {
        Log.i("TAG", "onQuantityChange: $productId $quantity")

        if (quantity < 0) return
//        thread {
//            val cartId = if (cartItemId == null) {
//                cartRepository.addCartItem(CartItem(productId = productId, quantity = 1))
//            } else if (quantity == 0) {
//                // TODO delete item
////                cartRepository
//                cartItemId
//            } else {
//                cartRepository.updateQuantity(cartItemId, quantity)
//                cartItemId
//            }
//            val newItem = cartRepository.fetchCartItem(cartId)
//            _productInformation.postValue(
//                productInformation.value?.copy(
//                    cartItem = newItem
//                )
//            )
//        }
    }
}
