package woowacourse.shopping.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class CartViewModel : ViewModel() {
    var offSet: Int = 0
        private set

    private val _carts = MutableLiveData<UiState<List<Cart>>>(UiState.None)
    val carts: LiveData<UiState<List<Cart>>> get() = _carts

//    private val _maxOffSet = MutableLiveData<UiState<Int>>(UiState.None)
//    val maxOffSet: LiveData<UiState<Int>> get() = _maxOffSet

    var maxOffset: Int = 0
        private set

    init {
        loadProductByOffset()
        getItemCount()
    }

    fun plus() {
        Log.d("offset", "$offSet")
        Log.d("maxOffset ", "$maxOffset")
        if (offSet == maxOffset) return
        offSet++
        loadProductByOffset()
    }

    fun minus() {
        if (offSet == 0) return
        offSet--
        loadProductByOffset()
    }

    fun loadProductByOffset() {
        DummyCartRepository.load(offSet, 3).onSuccess {
            if (_carts.value is UiState.None || _carts.value is UiState.Error) {
                _carts.value = UiState.Finish(it)
            } else {
                _carts.value = UiState.Finish(it)
            }
        }.onFailure {
            _carts.value = UiState.Error("LOAD ERROR")
        }
    }

    fun deleteProduct(product: Product) {
        DummyCartRepository.delete(product).onSuccess {
            _carts.value = UiState.Finish(emptyList())
            loadProductByOffset()
            getItemCount()
        }.onFailure {
            _carts.value = UiState.Error("DELETE ERROR")
        }
    }

    fun getItemCount() {
        DummyCartRepository.getMaxOffset().onSuccess {
            Log.d(" ", "$maxOffset")
            maxOffset = it
        }
    }
}
