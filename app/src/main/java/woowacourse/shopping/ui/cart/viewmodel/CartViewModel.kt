package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.CartPageManager
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao

class CartViewModel(private val cartDao: CartDao) : ViewModel() {
    private val cartPageManager by lazy { CartPageManager(PAGE_SIZE) }
    private val _pageNumber: MutableLiveData<Int> = MutableLiveData()

    private val _canMoveNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMoveNextPage: LiveData<Boolean> get() = _canMoveNextPage

    private val _canMovePreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMovePreviousPage: LiveData<Boolean> get() = _canMovePreviousPage

    val pageNumber: LiveData<Int> get() = _pageNumber

    private val _cart: MutableLiveData<List<Product>> = MutableLiveData()
    val cart: LiveData<List<Product>> get() = _cart

    init {
        loadCartItems()
        updatePageState()
    }

    fun removeCartItem(productId: Long) {
        cartDao.delete(productId)
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(cartDao.itemSize())
        _cart.value = cartDao.getProducts(cartPageManager.pageNum, PAGE_SIZE)
    }

    fun plusPageNum() {
        cartPageManager.plusPageNum()
        loadCartItems()
        updatePageState()
    }

    fun minusPageNum() {
        cartPageManager.minusPageNum()
        loadCartItems()
        updatePageState()
    }

    private fun loadCartItems() {
        _cart.value = cartDao.getProducts(cartPageManager.pageNum, PAGE_SIZE)
    }

    private fun updatePageState() {
        _pageNumber.value = cartPageManager.pageNum
        _canMovePreviousPage.value = cartPageManager.canMovePreviousPage()
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(cartDao.itemSize())
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
