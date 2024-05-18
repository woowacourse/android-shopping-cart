package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.UiState

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    var maxPage: Int = 0
        private set
    var currentPage: Int = 0
        private set
        get() = field.coerceAtMost(maxPage)
    private val _carts = MutableLiveData<UiState<List<Cart>>>(UiState.None)

    val carts: LiveData<UiState<List<Cart>>> get() = _carts

    init {
        updateMaxPage()
    }

    fun loadProductByPage() {
        cartRepository.load(currentPage, PAGE_SIZE).onSuccess {
            _carts.value = UiState.Success(it)
        }.onFailure {
            _carts.value = UiState.Error(CART_LOAD_ERROR)
        }
    }

    private fun updateMaxPage() {
        cartRepository.getMaxPage(PAGE_SIZE).onSuccess {
            maxPage = it
        }
    }

    fun plus() {
        if (currentPage == maxPage) return
        currentPage++
        loadProductByPage()
    }

    fun minus() {
        if (currentPage == 0) return
        currentPage--
        loadProductByPage()
    }

    fun deleteProduct(product: Product) {
        cartRepository.delete(product).onSuccess {
            updateMaxPage()
            loadProductByPage()
        }.onFailure {
            _carts.value = UiState.Error(CART_DELETE_ERROR)
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
        const val CART_LOAD_ERROR = "LOAD ERROR"
        const val CART_DELETE_ERROR = "DELETE ERROR"
    }
}
