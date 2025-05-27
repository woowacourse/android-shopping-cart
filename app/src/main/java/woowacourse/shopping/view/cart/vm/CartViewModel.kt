package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.repository.CartRepository

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val page = Page(initialPage = INITIAL_PAGE_NUMBER, pageSize = PAGE_SIZE)
    private val _carts = MutableLiveData<List<Cart>>()
    val carts: LiveData<List<Cart>> = _carts

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    private fun updatePageNoText() {
        _pageNumber.postValue(page.getPageNumber())
    }

    fun deleteProduct(productId: Long) {
        cartRepository.deleteById(productId) {
            loadCarts()
        }
    }

    fun addPage() {
        page.moveToNextPage()
        loadCarts()
    }

    fun subPage() {
        page.moveToPreviousPage()
        loadCarts()
    }

    fun loadCarts() {
        val (offset, limit) = page.targetRange()
        cartRepository.getPaged(offset, limit) { carts: List<Cart> ->
            _carts.postValue(carts)
        }
        setPageState(offset, limit)
    }

    private fun setPageState(
        offset: Int,
        limit: Int,
    ) {
        cartRepository.totalSize { totalSize ->
            cartRepository.hasNextPage(offset + 1, limit) { hasNext ->
                cartRepository.hasOnlyPage(limit) { hasOnePage ->
                    _pageState.postValue(
                        PageState(
                            previousPageEnabled = page.hasPreviousPage(),
                            nextPageEnabled = hasNext,
                            pageVisibility = !hasOnePage,
                        ),
                    )
                }
            }
            updatePageNoText()
            page.resetToLastPageIfEmpty(carts.value?.size ?: 0) {
                loadCarts()
            }
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
            if (updated.quantity == 0) {
                cartRepository.deleteById(cart.product.id) {
                    _carts.postValue(
                        currentCarts.toMutableList().apply {
                            this[cartIndex] = updated
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
        private const val PAGE_SIZE = 5
    }
}
