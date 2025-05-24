package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val page = Page(initialPage = INITIAL_PAGE_NUMBER, pageSize = PAGE_SIZE)
    private val _carts = MutableLiveData(emptyList<Cart>())
    val carts: LiveData<List<Cart>> = _carts

    private val _totalQuantity = MutableLiveData(0)
    val totalQuantity: LiveData<Int> = _totalQuantity

    private val _loadable = MutableLiveData(false)
    val loadable: LiveData<Boolean> = _loadable

    fun loadProducts() {
        val (offset, limit) = page.targetRange()
        cartRepository.getPagedShopItems(page.currentPage, limit) { carts: List<Cart> ->
            _carts.postValue((this.carts.value ?: emptyList()) + carts)
            _loadable.postValue(productRepository.notHasMoreProduct(page.currentPage, limit).not())
            _totalQuantity.postValue((totalQuantity.value ?: 0) + carts.sumOf { it.quantity })
        }
    }

    fun moveNextPage() {
        page.moveToNextPage()
        loadProducts()
    }

    fun addCart(cart: Cart) {
        val currentCarts = carts.value ?: return
        val cartIndex = currentCarts.indexOfFirst { cart.product.id == it.product.id }
        val updated = cart.increase()
        cartRepository.insert(updated) {
            _carts.postValue(
                currentCarts.toMutableList().apply {
                    this[cartIndex] = updated
                },
            )
            _totalQuantity.postValue((totalQuantity.value ?: 0) + 1)
        }
    }

    fun plusCartQuantity(cart: Cart) {
        val currentCarts = carts.value ?: return
        val cartIndex = currentCarts.indexOfFirst { cart.product.id == it.product.id }
        val updated = cart.increase()
        cartRepository.update(updated) {
            _carts.postValue(
                currentCarts.toMutableList().apply {
                    this[cartIndex] = updated
                },
            )
            _totalQuantity.postValue((totalQuantity.value ?: 0) + 1)
        }
    }

    fun minusCartQuantity(cart: Cart) {
        val currentCarts = carts.value ?: return
        val cartIndex = currentCarts.indexOfFirst { cart.product.id == it.product.id }
        val updated = cart.decrease()
        cartRepository.update(updated) {
            if (updated.quantity == 0) {
                cartRepository.deleteById(cart.product.id) {
                    _carts.postValue(
                        currentCarts.toMutableList().apply {
                            this[cartIndex] = updated
                        },
                    )
                    _totalQuantity.postValue((totalQuantity.value ?: 0) - 1)
                }
                return@update
            }
            _carts.postValue(
                currentCarts.toMutableList().apply {
                    this[cartIndex] = updated
                },
            )
            _totalQuantity.postValue((totalQuantity.value ?: 0) - 1)
        }
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 20
    }
}
