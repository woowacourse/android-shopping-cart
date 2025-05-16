package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.ShoppingCartRepository
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.cart.adapter.ShoppingCartEventHandler

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel(),
    ShoppingCartEventHandler {
    private val _products = MutableLiveData<List<ShoppingProduct>>()
    val products: LiveData<List<ShoppingProduct>> = _products

    private val _removedProduct = MutableLiveData<ShoppingProduct>()
    val removedProduct: LiveData<ShoppingProduct> = _removedProduct

    private var _page = MutableLiveData(FIRST_PAGE_NUMBER)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(false)
    val hasNext: LiveData<Boolean> = _hasNext

    init {
        loadPage(FIRST_PAGE_NUMBER)
    }

    override fun onProductRemoveClick(item: ShoppingProduct) {
        deleteProduct(item)
    }

    fun loadNextProducts() {
        _page.value = _page.value?.plus(1)
        loadPage(_page.value ?: FIRST_PAGE_NUMBER)
    }

    fun loadPreviousProducts() {
        _page.value = _page.value?.minus(1)
        loadPage(_page.value ?: FIRST_PAGE_NUMBER)
    }

    private fun loadPage(page: Int) {
        val result = repository.getPagedProducts(PAGE_SIZE, (page - 1) * PAGE_SIZE)
        _hasNext.value = result.hasNext
        _products.value = result.items
    }

    private fun deleteProduct(product: ShoppingProduct) {
        repository.delete(product.id)
        _removedProduct.value = product
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
