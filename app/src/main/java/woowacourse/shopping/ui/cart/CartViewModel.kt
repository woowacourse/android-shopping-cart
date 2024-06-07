package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import woowacourse.shopping.data.CartRepository
import woowacourse.shopping.data.mapper.toModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.livedata.MutableSingleLiveData
import woowacourse.shopping.ui.livedata.SingleLiveData

sealed interface CartUiState {
    data class Success(
        val cartProducts: List<Product>,
    ) : CartUiState

    data object Loading : CartUiState
    data object Error : CartUiState
}

class CartViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _uiState: MutableLiveData<CartUiState> =
        MutableLiveData(CartUiState.Loading)
    val uiState: LiveData<CartUiState> get() = _uiState

    val isLoading: LiveData<Boolean> = uiState.map {
        it is CartUiState.Loading
    }

    private val _onCartProductDeleted = MutableSingleLiveData<Unit>()
    val onCartProductDeleted: SingleLiveData<Unit> get() = _onCartProductDeleted

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _uiState.value = CartUiState.Error
        }

    fun getAllCartProducts() {
        viewModelScope.launch(coroutineExceptionHandler) {
            supervisorScope {
                launch { throw RuntimeException("또다른 에러 발생!") }
                val cartProducts = cartRepository
                    .getAllCartProducts()
                    .map { it.toModel() }
                _uiState.value = CartUiState.Success(cartProducts)
            }
        }
    }

    fun deleteCartProduct(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            cartRepository.deleteCartProduct(id.toLong())
            _onCartProductDeleted.setValue(Unit)
        }
    }
}
