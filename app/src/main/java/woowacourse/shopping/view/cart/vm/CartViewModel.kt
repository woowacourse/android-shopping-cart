package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE

class CartViewModel(private val cartStorage: CartStorage) : ViewModel() {
    private val paging = Paging(initialPage = INITIAL_PAGE_NO, pageSize = PAGE_SIZE)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    private fun updatePageNoText() {
        _pageNumber.value = paging.getPageNumber()
    }

    fun deleteProduct(productId: Long) {
        cartStorage.deleteProduct(productId)
        loadCarts()

        if (paging.resetToLastPageIfEmpty(_products.value)) {
            loadCarts()
        }
    }

    fun addPage() {
        paging.moveToNextPage()
        loadCarts()
    }

    fun subPage() {
        paging.moveToPreviousPage()
        loadCarts()
    }

    fun loadCarts() {
        val products = cartStorage.getProducts(paging.getPageNumber() - 1, PAGE_SIZE)
        _products.value = products
        _pageState.value = paging.createPageState(cartStorage)
        updatePageNoText()
    }
}
