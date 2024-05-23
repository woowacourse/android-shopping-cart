package woowacourse.shopping.ui.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartPage
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductDao
import kotlin.math.min

class CartViewModel(
    private val productDao: ProductDao,
    applicationContext: Context,
) : ViewModel() {
    private val ordersRepository = OrdersRepository(applicationContext)

    private val _cartPage: MutableLiveData<CartPage> = MutableLiveData()
    val cartPage: LiveData<CartPage> get() = _cartPage

    private val _cart: MutableLiveData<MutableMap<Long, Product>> = MutableLiveData(mutableMapOf())
    val cart: LiveData<MutableMap<Long, Product>> get() = _cart

    private val allProducts = productDao.findAll()

    private val allOrders get() = ordersRepository.getAllData()

    private val _orderCounts: MutableLiveData<MutableMap<Long, Int>> =
        MutableLiveData(mutableMapOf())
    val orderCounts: LiveData<MutableMap<Long, Int>> get() = _orderCounts

    private val _orderPrices: MutableLiveData<MutableMap<Long, Int>> =
        MutableLiveData(mutableMapOf())
    val orderPrices: LiveData<MutableMap<Long, Int>> get() = _orderPrices

    val cartItems
        get() =
            allOrders.map { orderEntity ->
                allProducts.first { it.id == orderEntity.productId }
            }

    fun loadCartItems() {
        _cartPage.value = CartPage()
        renewCart()
        _orderCounts.value = getProductsQuantities()
        _orderPrices.value = getProductsTotalPrices()
    }

    fun removeCartItem(productId: Long) {
        ordersRepository.deleteById(productId)
        renewCart()
        val currentPage = _cartPage.value?.number
        _cartPage.value = CartPage(currentPage ?: -1)
    }

    fun plusPageNum() {
        _cartPage.value = _cartPage.value?.plus()
        renewCart()
    }

    fun minusPageNum() {
        _cartPage.value = _cartPage.value?.minus()
        renewCart()
    }

    private fun getProducts(): List<Product> {
        val fromIndex = (cartPage.value!!.number - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, cartItems.size)
        return cartItems.subList(fromIndex, toIndex)
    }

    private fun renewCart() {
        _cart.value!!.clear()
        getProducts().forEach {
            _cart.value!![it.id] = it
        }
        _cart.value = _cart.value
    }

    private fun getProductsQuantities(): MutableMap<Long, Int> {
        val productsQuantities: MutableMap<Long, Int> = mutableMapOf()
        cartItems.forEach { product ->
            productsQuantities[product.id] = allOrders.first { it.productId == product.id }.quantity
        }
        return productsQuantities
    }

    private fun getProductsTotalPrices(): MutableMap<Long, Int> {
        val productsPrices: MutableMap<Long, Int> = mutableMapOf()
        allProducts.forEach {
            productsPrices[it.id] = it.price
        }
        val productsQuantities = getProductsQuantities()
        val productsTotalPrices: MutableMap<Long, Int> = mutableMapOf()
        allOrders.forEach {
            productsTotalPrices[it.productId] = productsPrices[it.productId]!! * productsQuantities[it.productId]!!
        }
        return productsTotalPrices
    }

    companion object {
        private const val OFFSET = 1
        private const val PAGE_SIZE = 5
    }
}
