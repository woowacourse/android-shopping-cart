package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartDao
import woowacourse.shopping.model.Product
import kotlin.math.min

class CartViewModel(private val cartDao: CartDao) : ViewModel() {
    private var pageNum = 1
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
        _pageNumber.value = pageNum

        items.clear()
        items.addAll(cartDao.findAll())
        _cart.value = getProducts()

        _canMovePreviousPage.value = canMovePreviousPage()
        _canMoveNextPage.value = canMoveNextPage()
    }

    fun removeCartItem(productId: Long) {
        cartDao.delete(productId)
        items.remove(items.find { it.id == productId })
        _cart.value = getProducts()
    }

    fun plusPageNum() {
        pageNum += 1
    }

    fun minusPageNum() {
        pageNum -= 1
    }

    private fun canMovePreviousPage(): Boolean {
        return pageNum != 1
    }

    private fun canMoveNextPage(): Boolean {
        val fromIndex = (pageNum - 1) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, items.size)
        return toIndex != (items.size)
    }

    private fun getProducts(): List<Product> {
        val fromIndex = (pageNum - 1) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, items.size)
        return items.toList().subList(fromIndex, toIndex)
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}
