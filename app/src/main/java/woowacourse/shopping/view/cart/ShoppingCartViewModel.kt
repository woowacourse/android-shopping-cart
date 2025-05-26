package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartProductRepository

class ShoppingCartViewModel(
    private val repository: CartProductRepository,
) : ViewModel(),
    ShoppingCartEventHandler {
    private val cartProducts = mutableListOf<CartProduct>()

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
        repository.deleteByProductId(item.product.id) {
            cartProducts.removeIf { it.product.id == item.product.id }

            val currentPage = page.value ?: FIRST_PAGE_NUMBER
            val startIndex = (currentPage - 1) * PAGE_SIZE

            if (startIndex >= cartProducts.size && currentPage > FIRST_PAGE_NUMBER) {
                loadPage(currentPage - 1)
            } else {
                loadPage(currentPage)
            }
        }
    }

    override fun onQuantityIncreaseClick(item: CartProduct) {
        updateQuantity(item, item.quantity + 1)
    }

    override fun onQuantityDecreaseClick(item: CartProduct) {
        if (item.quantity == 1) return
        updateQuantity(item, item.quantity - 1)
    }

    private fun loadPage(page: Int) {
        val offset = (page - 1) * PAGE_SIZE
        val end = offset + PAGE_SIZE

        if (cartProducts.size < end) {
            repository.getPagedProducts(end - cartProducts.size, cartProducts.size) { result ->
                cartProducts.addAll(result.items)
                val hasNext = result.hasNext
                updatePageState(page, offset, end, hasNext)
            }
        } else {
            checkHasNext(end) {
                val hasNext = cartProducts.size > end || it
                updatePageState(page, offset, end, hasNext)
            }
        }
    }

    private fun updatePageState(
        page: Int,
        offset: Int,
        end: Int,
        hasNext: Boolean,
    ) {
        _products.postValue(cartProducts.subList(offset, cartProducts.size.coerceAtMost(end)))
        _page.postValue(page)
        val hasPrevious = page > FIRST_PAGE_NUMBER
        _hasPrevious.postValue(hasPrevious)
        _hasNext.postValue(hasNext)
        _isSinglePage.postValue(!hasNext && !hasPrevious)
    }

    private fun updateQuantity(
        item: CartProduct,
        newQuantity: Int,
    ) {
        repository.updateQuantity(item.product.id, item.quantity, newQuantity) {
            val index = cartProducts.indexOfFirst { it.product.id == item.product.id }
            if (index != -1) {
                cartProducts[index] = cartProducts[index].copy(quantity = newQuantity)
            }
            loadPage(_page.value ?: FIRST_PAGE_NUMBER)
        }
    }

    private fun checkHasNext(
        offset: Int,
        callback: (Boolean) -> Unit,
    ) {
        repository.getPagedProducts(1, offset) { result ->
            callback(result.items.isNotEmpty())
        }
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
