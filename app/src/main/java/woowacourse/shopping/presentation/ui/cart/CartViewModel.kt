package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.ProductCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.ErrorEventState
import woowacourse.shopping.presentation.ui.UiState

class CartViewModel(private val productCartRepository: ProductCartRepository) : ViewModel() {
    var maxOffset: Int = 0
        private set

    var offSet: Int = 0
        private set
        get() = field.coerceAtMost(maxOffset)
    private val _carts = MutableLiveData<UiState<List<Cart>>>(UiState.None)

    val carts: LiveData<UiState<List<Cart>>> get() = _carts

    private val _errorHandler = MutableLiveData<ErrorEventState<String>>()
    val errorHandler: LiveData<ErrorEventState<String>> get() = _errorHandler

    init {
        getItemCount()
    }

    fun loadProductByOffset() {
        productCartRepository.findByPaging(offSet, PAGE_SIZE).onSuccess {
            if (_carts.value is UiState.None) {
                _carts.value = UiState.Success(it)
            } else {
                _carts.value = UiState.Success(it)
            }
        }.onFailure {
            _errorHandler.value = ErrorEventState(CART_LOAD_ERROR)
        }
    }

    fun plus() {
        if (offSet == maxOffset) return
        offSet++
        loadProductByOffset()
    }

    fun minus() {
        if (offSet == 0) return
        offSet--
        loadProductByOffset()
    }

    fun deleteProduct(product: Product) {
        productCartRepository.delete(product).onSuccess {
            getItemCount()
            loadProductByOffset()
        }.onFailure {
            _errorHandler.value = ErrorEventState(CART_DELETE_ERROR)
        }
    }

    private fun getItemCount() {
        productCartRepository.getMaxOffset().onSuccess {
            maxOffset = it
        }
    }

    companion object {
        const val CART_LOAD_ERROR = "LOAD ERROR"
        const val CART_DELETE_ERROR = "DELETE ERROR"
        const val PAGE_SIZE = 5
        const val PAGE_UPPER_BOUND = 4
    }
}
