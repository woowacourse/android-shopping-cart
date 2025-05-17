package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.ShoppingCartRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.cart.adapter.ShoppingCartEventHandler

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel(),
    ShoppingCartEventHandler {
    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    private val _removedProduct = MutableLiveData<CartProduct>()
    val removedProduct: LiveData<CartProduct> = _removedProduct

    private var _page = MutableLiveData(FIRST_PAGE_NUMBER)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(false)
    val hasNext: LiveData<Boolean> = _hasNext

    private val _hasPrevious = MutableLiveData(false)
    val hasPrevious: LiveData<Boolean> = _hasPrevious

    init {
        loadPage(FIRST_PAGE_NUMBER)
    }

    override fun onProductRemoveClick(item: CartProduct) {
        deleteProduct(item)
    }

    fun loadNextProducts() {
        if (_hasNext.value != true) return
        val nextPage = _page.value?.plus(1) ?: FIRST_PAGE_NUMBER
        loadPage(nextPage)
        _page.value = nextPage
    }

    fun loadPreviousProducts() {
        if (_hasPrevious.value != true) return
        val previousPage = _page.value?.minus(1) ?: FIRST_PAGE_NUMBER
        loadPage(previousPage)
        _page.value = previousPage
    }

    private fun loadPage(page: Int) {
        val result = repository.getPagedProducts(PAGE_SIZE, (page - 1) * PAGE_SIZE)
        _hasNext.value = result.hasNext
        _hasPrevious.value = page != FIRST_PAGE_NUMBER
        _products.value = result.items
    }

    private fun deleteProduct(product: CartProduct) {
        repository.delete(product.id)
        _removedProduct.value = product
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
