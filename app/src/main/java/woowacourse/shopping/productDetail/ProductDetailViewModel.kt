package woowacourse.shopping.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore

class ProductDetailViewModel(val productId: Int) : ViewModel() {
    private val productStore = DummyProductStore()

    val product: Product
        get() = productStore.findById(productId)

    private val _cartItem = MutableLiveData(
        ShoppingCart.cartItems.find { it.productId == productId } ?: CartItem(productId, 0)
    )
    val cartItem: LiveData<CartItem> get() = _cartItem

    fun addProductToCart() {
        if (ShoppingCart.cartItems.any { it.productId == productId }) {
            addProductCount()
            return
        }
        ShoppingCart.addProductToCart(productId)
        _cartItem.value = ShoppingCart.cartItems.find { it.productId == productId }
    }

    private fun addProductCount() {
        ShoppingCart.addProductCount(productId)
        _cartItem.value = ShoppingCart.cartItems.find { it.productId == productId }
    }

    fun subtractProductCount() {
        ShoppingCart.subtractProductCount(productId)
        _cartItem.value = ShoppingCart.cartItems.find { it.productId == productId }
    }

    companion object {
        fun factory(productId: Int): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductDetailViewModel(productId) }
        }
    }
}
