package woowacourse.shopping.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartPageManager
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.ui.CountButtonClickListener

class CartViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel(), CountButtonClickListener {
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
                ProductWithQuantity(productRepository.find(cart.productId), cart.quantity)
            }
        }

    init {
        loadCartItems()
        updatePageState()
    }

    fun removeCartItem(productId: Long) {
        cartRepository.deleteByProductId(productId)
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(cartRepository.itemSize())
        cart.value = cartRepository.getProducts(cartPageManager.pageNum, PAGE_SIZE)
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

    override fun plusCount(productId: Long) {
        cartRepository.plusQuantityByProductId(productId)
        loadCartItems()
    }

    override fun minusCount(productId: Long) {
        cartRepository.minusQuantityByProductId(productId)
        loadCartItems()
    }

    private fun loadCartItems() {
        cart.value = cartRepository.getProducts(cartPageManager.pageNum, PAGE_SIZE)
    }

    private fun updatePageState() {
        _pageNumber.value = cartPageManager.pageNum
        _canMovePreviousPage.value = cartPageManager.canMovePreviousPage()
        _canMoveNextPage.value = cartPageManager.canMoveNextPage(cartRepository.itemSize())
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}
