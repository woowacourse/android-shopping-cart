package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart

class ShoppingCartViewModel(
    private val repository: CartProductRepository,
) : ViewModel(),
    ShoppingCartEventHandler {
    private var shoppingCart = ShoppingCart()

    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    private var _page = MutableLiveData(FIRST_PAGE_NUMBER)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(false)
    val hasNext: LiveData<Boolean> = _hasNext

    private val _hasPrevious = MutableLiveData(false)
    val hasPrevious: LiveData<Boolean> = _hasPrevious

    private val _isSinglePage = MutableLiveData(true)
    val isSinglePage: LiveData<Boolean> = _isSinglePage

    init {
        loadPage(FIRST_PAGE_NUMBER)
    }

    override fun loadNextProducts() {
        val nextPage = page.value?.plus(1) ?: FIRST_PAGE_NUMBER
        if (hasNext.value == true) loadPage(nextPage)
    }

    override fun loadPreviousProducts() {
        val prevPage = page.value?.minus(1) ?: FIRST_PAGE_NUMBER
        if (_hasPrevious.value == true) loadPage(prevPage)
    }

    override fun onProductRemoveClick(item: CartProduct) {
        repository.deleteByProductId(item.product.id)
        shoppingCart -= item
        reloadCurrentPage()
    }

    override fun onQuantityIncreaseClick(item: Product) {
        shoppingCart += item
        repository.updateQuantity(item.id, shoppingCart.getQuantity(item))
        reloadCurrentPage()
    }

    override fun onQuantityDecreaseClick(item: Product) {
        if (shoppingCart.getQuantity(item) == 1) return
        shoppingCart -= item
        repository.updateQuantity(item.id, shoppingCart.getQuantity(item))
        reloadCurrentPage()
    }

    private fun loadPage(page: Int) {
        val offset = (page - 1) * PAGE_SIZE
        val end = offset + PAGE_SIZE

        if (shoppingCart.cartProducts.size < end) {
            val result =
                repository.getPagedProducts(
                    end - shoppingCart.cartProducts.size,
                    shoppingCart.cartProducts.size,
                )
            shoppingCart += result.items
            _hasNext.value = result.hasNext
        } else {
            _hasNext.value = shoppingCart.cartProducts.size > end || checkHasNext(end)
        }

        _products.value =
            shoppingCart.cartProducts
                .toList()
                .sortedBy { it.id }
                .subList(offset, shoppingCart.cartProducts.size.coerceAtMost(end))

        _hasPrevious.value = page > FIRST_PAGE_NUMBER
        _page.value = page
        _isSinglePage.value =
            page == FIRST_PAGE_NUMBER &&
            hasNext.value == false &&
            shoppingCart.cartProducts.size <= PAGE_SIZE
    }

    private fun reloadCurrentPage() {
        val currentPage = page.value ?: FIRST_PAGE_NUMBER
        loadPage(currentPage)

        if (products.value.isNullOrEmpty() && currentPage > FIRST_PAGE_NUMBER) {
            loadPage(currentPage - 1)
        }
    }

    private fun checkHasNext(offset: Int): Boolean = repository.getPagedProducts(1, offset).items.isNotEmpty()

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
