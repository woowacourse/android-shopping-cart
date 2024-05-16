package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartDao
import woowacourse.shopping.model.CartPageManager
import woowacourse.shopping.model.Product
import kotlin.math.min

class CartViewModel(private val cartDao: CartDao) : ViewModel() {
    private val cartPageManager by lazy { CartPageManager() }
    private val _pageNumber: MutableLiveData<Int> = MutableLiveData()

    private val _canMoveNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMoveNextPage: LiveData<Boolean> get() = _canMoveNextPage

    private val _canMovePreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMovePreviousPage: LiveData<Boolean> get() = _canMovePreviousPage

    val pageNumber: LiveData<Int> get() = _pageNumber

    private val items: MutableList<Product> = mutableListOf()
    private val _cart: MutableLiveData<List<Product>> = MutableLiveData()
    val cart: LiveData<List<Product>> get() = _cart

    fun loadCartItems() {
        _pageNumber.value = cartPageManager.pageNum

        items.clear()
        items.addAll(cartDao.findAll())
        _cart.value = getProducts()

        _canMovePreviousPage.value = cartPageManager.canMovePreviousPage()
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(items.size)
    }

    fun removeCartItem(productId: Long) {
        cartDao.delete(productId)
        items.remove(items.find { it.id == productId })
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(items.size)
        _cart.value = getProducts()
    }

    fun plusPageNum() {
        cartPageManager.plusPageNum()
    }

    fun minusPageNum() {
        cartPageManager.minusPageNum()
    }

    private fun getProducts(): List<Product> {
        val fromIndex = (cartPageManager.pageNum - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, items.size)
        return items.toList().subList(fromIndex, toIndex)
    }

    companion object {
        private const val OFFSET = 1
        private const val PAGE_SIZE = 5
    }
}
