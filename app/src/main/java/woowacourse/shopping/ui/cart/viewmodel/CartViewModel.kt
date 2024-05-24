package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.model.CartPageManager
import woowacourse.shopping.model.ProductWithQuantity
import kotlin.concurrent.thread

class CartViewModel(
    private val productDao: ProductDao,
    private val cartRepository: CartRepository,
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
            carts.map { cart ->
                ProductWithQuantity(productDao.find(cart.productId), cart.quantity)
            }
        }

    init {
        loadCartItems()
        updatePageState()
    }

    fun removeCartItem(productId: Long) {
        thread {
            cartRepository.deleteByProductId(productId)
            _canMoveNextPage.postValue(cartPageManager.canMoveNextPage(cartRepository.itemSize()))
            cart.postValue(cartRepository.getProducts(cartPageManager.pageNum, PAGE_SIZE))
        }.join()
        loadCartItems()
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

    fun plusCount(productId: Long) {
        thread {
            cartRepository.plusQuantityByProductId(productId)
        }.join()
        loadCartItems()
    }

    fun minusCount(productId: Long) {
        thread {
            cartRepository.minusQuantityByProductId(productId)
            loadCartItems()
        }.start()
    }

    private fun loadCartItems() {
        thread {
            cart.postValue(cartRepository.getProducts(cartPageManager.pageNum, PAGE_SIZE))
        }.join()
    }

    private fun updatePageState() {
        _pageNumber.value = cartPageManager.pageNum
        _canMovePreviousPage.value = cartPageManager.canMovePreviousPage()
        thread {
            _canMoveNextPage.postValue(cartPageManager.canMoveNextPage(cartRepository.itemSize()))
        }.join()
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
