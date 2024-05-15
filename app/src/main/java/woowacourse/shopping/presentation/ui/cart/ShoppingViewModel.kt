package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class CartViewModel : ViewModel() {
    private var offSet: Int = 0

    private val _carts = MutableLiveData<UiState<List<Cart>>>(UiState.None)
    val carts get() = _carts

    init {
        loadProductByOffset()
    }

    fun loadNext() {
        loadProductByOffset()
        offSet++
    }

    fun loadPrevious() {
        loadProductByOffset()
        offSet--
    }

    fun loadProductByOffset() {
        DummyCartRepository.load(offSet, 5).onSuccess {
            if (_carts.value is UiState.None || _carts.value is UiState.Error) {
                _carts.value = UiState.Finish(it)
            } else {
                _carts.value = UiState.Finish((_carts.value as UiState.Finish).data + it)
            }
        }.onFailure {
            _carts.value = UiState.Error("LOAD ERROR")
        }
    }

    fun deleteProduct(product: Product) {
        DummyCartRepository.delete(product).onSuccess {
            _carts.value = UiState.Finish(emptyList())
            loadProductByOffset()
        }.onFailure {
            _carts.value = UiState.Error("DELETE ERROR")
        }
    }
}
