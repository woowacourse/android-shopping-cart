package woowacourse.shopping.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.providers.RepositoryProvider

class CartViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData(CartProductsUiState())
    val uiState: LiveData<CartProductsUiState> get() = _uiState

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> get() = _pageNumber

    val isFirstPage: Boolean
        get() = pageNumber.value == 1
    var isLastPage: Boolean = false
        private set

    init {
        loadCartProducts()
    }

    fun loadCartProducts() {
        val pageNumber = pageNumber.value ?: 1

        repository.fetchInRange(
            PAGE_FETCH_SIZE,
            (pageNumber - 1) * PAGE_SIZE
        ) { result ->
            result.onSuccess { cartProducts ->
                isLastPage =
                    cartProducts.size != PAGE_FETCH_SIZE // 비동기로 uistate 에서 관리하면 값이 늦게 갱신되고 있음..
                val visibleProductsSize = PAGE_SIZE.coerceAtMost(cartProducts.size)
                handleUpdateItems(visibleProductsSize, cartProducts)
            }
        }
    }

    fun isVisiblePagination(): Boolean {
        Log.d("CN_Log", "isLastPage =${isLastPage}")
        Log.d("CN_Log", "pageNumber = ${pageNumber.value}")
        return !(isLastPage && pageNumber.value == 1)
    }

    fun moveToPrevious() {
        if (!isFirstPage) {
            _pageNumber.postValue(pageNumber.value?.minus(1))
        }
    }

    fun moveToNext() {
        if (!isLastPage) {
            _pageNumber.value = pageNumber.value?.plus(1)
        }
    }

    fun deleteProduct(cartId: Long) {
        repository.delete(cartId) {
            loadCartProducts()
        }
    }

    private fun handleUpdateItems(
        visibleProductsSize: Int,
        products: List<CartProduct>
    ) {
        val cartProductsUiState = uiState.value ?: return
        if (hasPages(visibleProductsSize)) {
            moveToPrevious()
            return
        }

        val updateItems = products.take(visibleProductsSize)
        _uiState.postValue(cartProductsUiState.loadPage(updateItems))
    }

    private fun hasPages(visibleProductsSize: Int): Boolean {
        return visibleProductsSize == 0 && !isFirstPage
    }

    fun increaseQuantity(productId: Long) {
        val cartProductsUiState = uiState.value ?: return
        repository.updateQuantity(productId, 1) { result ->
            result.onSuccess {
                _uiState.postValue(cartProductsUiState.increaseQuantity(productId))
            }
        }
    }

    fun decreaseQuantity(productId: Long) {
        val cartProductsUiState = uiState.value ?: return
        repository.updateQuantity(productId, -1) { result ->
            result.onSuccess {
                _uiState.postValue(cartProductsUiState.decreaseQuantity(productId))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CartViewModel(RepositoryProvider.provideCartRepository()) as T
                }
            }
        private const val PAGE_FETCH_SIZE = 6
        private const val PAGE_SIZE = 5
    }
}
