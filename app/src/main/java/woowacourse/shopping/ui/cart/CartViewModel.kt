package woowacourse.shopping.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.model.Product

class CartViewModel(
    private val cartRepository: CartRepository = CartRepository,
) : ViewModel() {

    private val _cartProducts: MutableLiveData<List<Product>> =
        MutableLiveData(emptyList())
    val cartProducts: LiveData<List<Product>> get() = _cartProducts

    private val _onCartProductDeleted: MutableLiveData<Boolean> = MutableLiveData(false)
    val onCartProductDeleted: LiveData<Boolean> get() = _onCartProductDeleted

    fun getAllCartProducts() {
        val allCartProducts = cartRepository.getAllCartProducts()
        Log.e("dino_log", "allCardProducts size: ${allCartProducts.size}")
        _cartProducts.value = allCartProducts
    }

    fun deleteCartProduct(id: Int) {
        cartRepository.deleteCartProduct(id)
        _onCartProductDeleted.value = true
    }
}
