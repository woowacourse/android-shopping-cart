package woowacourse.shopping.ui.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartPage
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductDao
import kotlin.math.min

class CartViewModel(
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    applicationContext: Context,
) : ViewModel() {
    private val ordersRepository = OrdersRepository(applicationContext)

    private val _cartPage: MutableLiveData<CartPage> = MutableLiveData()
    val cartPage: LiveData<CartPage> get() = _cartPage

    private val _cart: MutableLiveData<List<Product>> = MutableLiveData()
    val cart: LiveData<List<Product>> get() = _cart

    private val allProducts = productDao.findAll()

    private val allOrders get() = ordersRepository.getAllData()

    private val _orderCount: MutableLiveData<List<Int>> = MutableLiveData()
    val orderCount: LiveData<List<Int>> get() = _orderCount

    val cartItems
        get() =
            allOrders.map { orderEntity ->
                allProducts.first { it.id == orderEntity.productId }
            }

    fun loadCartItems() {
        _cartPage.value = CartPage()
        _cart.value = getProducts()
        _orderCount.value = getProductsCount()
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

    private fun getProductsCount(): List<Int> {
        val fromIndex = (cartPage.value!!.number - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, cartItems.size)
        return allOrders.map { it.quantity }.subList(fromIndex, toIndex)
    }

    companion object {
        private const val OFFSET = 1
        private const val PAGE_SIZE = 5
    }
}
