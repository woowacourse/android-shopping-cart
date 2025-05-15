package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Product

class CartViewModel(private val cartStorage: CartStorage) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _removeItemPosition = MutableLiveData<Int>()
    val removeItemPosition: LiveData<Int> = _removeItemPosition

    private val _pageState: MutableLiveData<PageState> = MutableLiveData(PageState())
    val pageState: LiveData<PageState> get() = _pageState

    private val _pageNo = MutableLiveData(INITIAL_PAGE_NO)
    val pageNo: LiveData<Int> = _pageNo

    private var hasEverShownNextPage = false

    fun deleteProduct(productId: Long) {
        val position = _products.value?.indexOfFirst { it.id == productId } ?: -1
        _products.value = _products.value?.filter { it.id != productId }

        _removeItemPosition.value = position
        cartStorage.deleteProduct(productId)
    }

    fun addPage() {
        val next = (pageNo.value ?: INITIAL_PAGE_NO) + 1
        _pageNo.value = next
        loadCarts(next, PAGE_SIZE)
    }

    fun subPage() {
        val prev = (pageNo.value ?: INITIAL_PAGE_NO) - 1
        if (prev < INITIAL_PAGE_NO) return
        _pageNo.value = prev
        loadCarts(prev, PAGE_SIZE)
    }

    fun loadCarts(
        pageNo: Int,
        pageSize: Int,
    ) {
        val nextPageIndex = pageNo - 1
        val loadedProducts = cartStorage.getProducts(nextPageIndex, pageSize)
        _products.value = loadedProducts

        updatePageState(pageNo, pageSize)
    }

    private fun updatePageState(
        pageNo: Int,
        pageSize: Int,
    ) {
        val hasNext = !cartStorage.notHasNextPage(pageNo, pageSize)
        if (hasNext) {
            hasEverShownNextPage = true
        }

        _pageState.value =
            _pageState.value?.copy(
                previousPageEnabled = pageNo > INITIAL_PAGE_NO,
                nextPageEnabled = hasNext,
                pageVisibility = hasEverShownNextPage,
            )
    }

    companion object {
        private const val INITIAL_PAGE_NO = 1
        private const val PAGE_SIZE = 5
    }
}
