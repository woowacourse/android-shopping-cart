package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartsImpl
import woowacourse.shopping.model.Product

class CartViewModel : ViewModel() {
    private val items: MutableList<Product> = mutableListOf()

    private val _cart: MutableLiveData<List<Product>> = MutableLiveData()
    val cart: LiveData<List<Product>> get() = _cart

    fun loadCartItems() {
        items.addAll(CartsImpl.findAll())
        _cart.value = items
    }

    fun removeCartItem(productId: Long) {
        CartsImpl.delete(productId)
        items.remove(items.find { it.id == productId })
        _cart.value = items
    }
}
