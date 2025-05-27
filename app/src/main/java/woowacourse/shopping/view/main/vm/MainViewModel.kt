package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class MainViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val page = Page(initialPage = INITIAL_PAGE_NUMBER, pageSize = PAGE_SIZE)
    private val _carts = MutableLiveData<List<Cart>>(emptyList())
    val carts: LiveData<List<Cart>> = _carts

    private val _recentProducts = MutableLiveData<List<RecentProduct>>(emptyList())
    val recentProducts: LiveData<List<RecentProduct>> = _recentProducts

    val recentProductsCount: LiveData<Int> = _recentProducts.map { it.size }

    val totalQuantity: LiveData<Int> = _carts.map { it.sumOf { it.quantity } }

    private val _loadable = MutableLiveData(false)
    val loadable: LiveData<Boolean> = _loadable

    fun loadProducts() {
        cartRepository.getPagedShopItems(page.currentPage, page.pageSize) { carts: List<Cart> ->
            _carts.postValue((this.carts.value ?: emptyList()) + carts)
            _loadable.postValue(
                productRepository.notHasMoreProduct(page.currentPage, page.pageSize).not(),
            )
        }
    }

    fun loadRecentProducts() {
        recentProductRepository.getAll { recentProducts ->
            this._recentProducts.postValue(recentProducts)
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
        }
    }

    fun minusCartQuantity(cart: Cart) {
        val currentCarts = carts.value ?: return
        val cartIndex = currentCarts.indexOfFirst { cart.product.id == it.product.id }
        val updated = cart.decrease()
        cartRepository.update(updated) {
            if (updated.quantity == 1) {
                cartRepository.deleteById(cart.product.id) {
                    _carts.postValue(
                        currentCarts.toMutableList().apply {
                            this[cartIndex] = updated.copy(quantity = 0)
                        },
                    )
                }
                return@update
            }
            _carts.postValue(
                currentCarts.toMutableList().apply {
                    this[cartIndex] = updated
                },
            )
        }
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 20
    }
}
