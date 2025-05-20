package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Product

class CartViewModel(private val cartStorage: CartStorage) : ViewModel() {
    private val page = Page(initialPage = INITIAL_PAGE_NUMBER, pageSize = PAGE_SIZE)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

    private fun updatePageNoText() {
        _pageNumber.value = page.getPageNumber()
    }

    fun deleteProduct(productId: Long) {
        cartStorage.deleteProduct(productId)
        loadCarts()

        if (page.resetToLastPageIfEmpty(products.value?.size ?: 0)) {
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
        val productsRange = page.targetRange(cartStorage.totalSize())
        val products = cartStorage.slice(productsRange)
        _products.value = products
        setPageState()
        updatePageNoText()
    }

    private fun setPageState() {
        val totalSize = cartStorage.totalSize()
        val hasNext = page.hasNextPage(totalSize)
        val isLastPage = page.isLastPage(totalSize)
        _pageState.value =
            PageState(
                previousPageEnabled = page.hasPreviousPage(),
                nextPageEnabled = hasNext,
                pageVisibility = hasNext || isLastPage,
            )
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
