package woowacourse.shopping.view.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
) : ViewModel(),
    QuantityController {
    private val _addToCart = MutableLiveData<Event<Unit>>()
    val addToCart: LiveData<Event<Unit>> = _addToCart

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> = _cartItem

    override fun increaseQuantity(productId: Long) {
        val current = _cartItem.value?.quantity ?: 1
        val newQuantity = current + 1
        _cartItem.value = _cartItem.value?.copy(quantity = newQuantity)
    }

    override fun decreaseQuantity(productId: Long) {
        val current = _cartItem.value?.quantity ?: 1
        if (current > 1) {
            val newQuantity = current - 1
            _cartItem.value = _cartItem.value?.copy(quantity = newQuantity)
        }
    }

    override fun updateQuantity() {
        val productId = _cartItem.value?.product?.id ?: 1
        val quantity = _cartItem.value?.quantity ?: 1
        cartRepository.update(productId, quantity)
    }

    fun onAddToCartClicked() {
        _addToCart.value = Event(Unit)
        cartRepository.add(_cartItem.value!!)
    }

    fun setCartItem(cartItem: CartItem) {
        _cartItem.value = cartItem
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return ProductDetailViewModel(
                        CartRepositoryImpl,
                    ) as T
                }
            }
    }
}
