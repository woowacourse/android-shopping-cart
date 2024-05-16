package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.UiState

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    var maxOffset: Int = 0
        private set

    var offSet: Int = 0
        private set
        get() = field.coerceAtMost(maxOffset)
    private val _carts = MutableLiveData<UiState<List<Cart>>>(UiState.None)

    val carts: LiveData<UiState<List<Cart>>> get() = _carts

    init {
        getItemCount()
    }

    fun loadProductByOffset() {
        cartRepository.load(offSet, 5).onSuccess {
            if (_carts.value is UiState.None || _carts.value is UiState.Error) {
                _carts.value = UiState.Finish(it)
            } else {
                _carts.value = UiState.Finish(it)
            }
        }.onFailure {
            _carts.value = UiState.Error(CART_LOAD_ERROR)
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
        cartRepository.delete(product).onSuccess {
            getItemCount()
            loadProductByOffset()
        }.onFailure {
            _carts.value = UiState.Error(CART_DELETE_ERROR)
        }
    }

    private fun getItemCount() {
        cartRepository.getMaxOffset().onSuccess {
            maxOffset = it
        }
    }

    companion object {
        const val CART_LOAD_ERROR = "LOAD ERROR"
        const val CART_DELETE_ERROR = "DELETE ERROR"
    }
}
