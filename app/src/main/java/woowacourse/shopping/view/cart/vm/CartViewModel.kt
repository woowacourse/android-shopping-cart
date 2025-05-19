package woowacourse.shopping.view.cart.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.cart.vm.Paging.Companion.INITIAL_PAGE_NO
import woowacourse.shopping.view.cart.vm.Paging.Companion.PAGE_SIZE

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val paging = Paging(initialPage = INITIAL_PAGE_NO, pageSize = PAGE_SIZE)

    private val _uiState = MutableLiveData<CartUiState>()
    val uiState: LiveData<CartUiState> = _uiState

    fun deleteProduct(cardId: Long) {
        cartRepository.delete(cardId)
        loadCarts()

        val currentProducts = _uiState.value?.products
        if (paging.resetToLastPageIfEmpty(currentProducts)) {
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

        val products = result.products.map { productRepository[it.productId] }
        val pageState = paging.createPageState(result.hasNextPage)

        _uiState.value = CartUiState(products = products, pageState = pageState)
    }
}
