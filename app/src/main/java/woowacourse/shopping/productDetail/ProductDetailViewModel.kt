package woowacourse.shopping.productDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore

class ProductDetailViewModel(application: Application, val productId: Int) :
    AndroidViewModel(application) {
    private val productStore = DummyProductStore()

    val product: Product
        get() = productStore.findById(productId)

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> get() = _cartItem

    init {
        loadCartItem()
    }

    private fun loadCartItem() {
        viewModelScope.launch {
            val cartItems = ShoppingCart.getCartItems()
            _cartItem.value = cartItems.find { it.productId == productId } ?: CartItem(productId, 0)
        }
    }

    fun addProductToCart() {
        viewModelScope.launch {
            if (_cartItem.value?.quantity ?: 0 > 0) {
                addProductCount()
            } else {
                ShoppingCart.addProductToCart(productId)
                loadCartItem()
            }
        }
    }

    private fun addProductCount() {
        viewModelScope.launch {
            ShoppingCart.addProductCount(productId)
            loadCartItem()
        }
    }

    fun subtractProductCount() {
        viewModelScope.launch {
            ShoppingCart.subtractProductCount(productId)
            loadCartItem()
        }
    }

    companion object {
        fun factory(application: Application, productId: Int): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductDetailViewModel(application, productId) }
        }
    }
}
