package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartPage
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import kotlin.math.min

class CartViewModel(private val cartDao: CartDao) : ViewModel() {
    private val _cartPage: MutableLiveData<CartPage> = MutableLiveData()
    val cartPage: LiveData<CartPage> get() = _cartPage

    private val _cart: MutableLiveData<List<Product>> = MutableLiveData()
    val cart: LiveData<List<Product>> get() = _cart

    val cartItems get() = cartDao.findAll()

    fun loadCartItems() {
        _cartPage.value = CartPage()
        _cart.value = getProducts()
    }

    fun removeCartItem(productId: Long) {
        cartDao.delete(productId)
        _cart.value = getProducts()
        val currentPage = _cartPage.value?.number
        _cartPage.value = CartPage(currentPage ?: -1)
    }

    fun plusPageNum() {
        _cartPage.value = _cartPage.value?.plus()
        _cart.value = getProducts()
    }

    fun minusPageNum() {
        _cartPage.value = _cartPage.value?.minus()
        _cart.value = getProducts()
    }

    private fun getProducts(): List<Product> {
        val fromIndex = (cartPage.value!!.number - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, cartItems.size)
        return cartItems.toList().subList(fromIndex, toIndex)
    }

    companion object {
        private const val OFFSET = 1
        private const val PAGE_SIZE = 5
    }
}
