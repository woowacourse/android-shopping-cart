package woowacourse.shopping.viewmodel.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.Cart

class CartViewModel(
    private val cart: Cart,
) : ViewModel() {
    private val _productsInCart = MutableLiveData(cart.productsInCart)
    val productsInCart = _productsInCart

    fun addToCart(product: Product) {
        cart.add(product)
        _productsInCart.value = cart.productsInCart
    }

    fun removeToCart(product: Product) {
        cart.remove(product)
        _productsInCart.value = cart.productsInCart
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

                    return CartViewModel(
                        Cart,
                    ) as T
                }
            }
    }
}
