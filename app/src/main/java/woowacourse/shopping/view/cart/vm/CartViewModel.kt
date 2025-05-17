package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val paging = Paging(initialPage = INITIAL_PAGE_NO, pageSize = PAGE_SIZE)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _pageState = MutableLiveData<PageState>()
    val pageState: LiveData<PageState> = _pageState

    fun deleteProduct(productId: Long) {
        cartRepository.delete(productId)
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
        val result = cartRepository.loadSinglePage(paging.getPageNo() - 1, PAGE_SIZE)

        _products.value = result.products
        _pageState.value = paging.createPageState(result.hasNextPage)
    }
}
