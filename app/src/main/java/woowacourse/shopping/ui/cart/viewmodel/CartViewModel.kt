package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.CartPageManager
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.CartsImpl.findProductWithQuantity
import woowacourse.shopping.model.data.ProductWithQuantityDao

class CartViewModel(
    private val productWithQuantityDao: ProductWithQuantityDao,
    private val cartDao: CartDao,
) : ViewModel() {
    private val cartPageManager by lazy { CartPageManager(PAGE_SIZE) }
    private val _pageNumber: MutableLiveData<Int> = MutableLiveData()

    private val _canMoveNextPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMoveNextPage: LiveData<Boolean> get() = _canMoveNextPage

    private val _canMovePreviousPage: MutableLiveData<Boolean> = MutableLiveData()
    val canMovePreviousPage: LiveData<Boolean> get() = _canMovePreviousPage

    val pageNumber: LiveData<Int> get() = _pageNumber

    private val cart: MutableLiveData<List<Cart>> = MutableLiveData()

    val productWithQuantity: LiveData<List<ProductWithQuantity>> =
        cart.map { carts ->
            carts.map { it.findProductWithQuantity() }
        }

    init {
        loadCartItems()
        updatePageState()
    }

    fun removeCartItem(productWithQuantityId: Long) {
        cartDao.deleteByProductWithQuantityId(productWithQuantityId)
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(cartDao.itemSize())
        cart.value = cartDao.getProducts(cartPageManager.pageNum, PAGE_SIZE)
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

    fun plusCount(productWithQuantityId: Long) {
        productWithQuantityDao.plusCartCount(productWithQuantityId)
        loadCartItems()
    }

    fun minusCount(productWithQuantityId: Long) {
        cartDao.minusQuantityByProductWithQuantityId(productWithQuantityId)
        loadCartItems()
    }

    private fun loadCartItems() {
        cart.value = cartDao.getProducts(cartPageManager.pageNum, PAGE_SIZE)
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
