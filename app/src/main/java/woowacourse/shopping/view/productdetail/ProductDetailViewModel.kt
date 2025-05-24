package woowacourse.shopping.view.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.cart.CartItem
import woowacourse.shopping.view.Event
import woowacourse.shopping.view.QuantityController

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    val cartItem: CartItem,
) : ViewModel(),
    QuantityController {
    private val _addToCart = MutableLiveData<Event<Unit>>()
    val addToCart: LiveData<Event<Unit>> = _addToCart

    private val _quantity = MutableLiveData(cartItem.quantity)
    val quantity: LiveData<Int> = _quantity

    override fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int,
    ) {
        val current = _quantity.value ?: INIT_QUANTITY
        val newQuantity = current + quantityIncrease
        _quantity.value = newQuantity
    }

    override fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int,
        minQuantity: Int,
    ) {
        val current = _quantity.value ?: INIT_QUANTITY
        if (current > minQuantity) {
            val newQuantity = current - quantityDecrease
            _quantity.value = newQuantity
        }
    }

    override fun updateQuantity() {
        val productId = cartItem.product.id
        val quantity = _quantity.value ?: INIT_QUANTITY
        cartRepository.update(productId, quantity)
    }

    fun onAddToCartClicked() {
        _addToCart.value = Event(Unit)
        val newCartItem = cartItem.copy(quantity = _quantity.value ?: INIT_QUANTITY)
        cartRepository.add(newCartItem)
    }

    companion object {
        private const val INIT_QUANTITY = 1

        class Factory(
            private val cartItem: CartItem,
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ProductDetailViewModel(
                    CartRepositoryImpl,
                    cartItem,
                ) as T
        }
    }
}
