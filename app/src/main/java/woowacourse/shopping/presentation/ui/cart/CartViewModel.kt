package woowacourse.shopping.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.presentation.ui.ErrorEventState
import woowacourse.shopping.presentation.ui.UiState
import kotlin.concurrent.thread

class CartViewModel(private val repository: Repository) : ViewModel(), CartActionHandler {
    var maxOffset: Int = 0
        private set

    var offSet: Int = 0
        private set
        get() = field.coerceAtMost(maxOffset)
    private val _carts = MutableLiveData<UiState<List<CartProduct>>>(UiState.None)

    val carts: LiveData<UiState<List<CartProduct>>> get() = _carts

    private val _errorHandler = MutableLiveData<ErrorEventState<String>>()
    val errorHandler: LiveData<ErrorEventState<String>> get() = _errorHandler

//    init {
//        getItemCount()
//    }

    fun loadProductByOffset() {
        thread {
            repository.findCartByPaging(offSet, PAGE_SIZE).onSuccess {
                _carts.postValue(UiState.Success(it))
            }.onFailure {
                _errorHandler.value = ErrorEventState(CART_LOAD_ERROR)
            }
        }
    }

//    private fun getItemCount() {
//        repository.getMaxOffset().onSuccess {
//            maxOffset = it
//        }
//    }
//
    override fun onDelete(productId: Long) {
//        repository.delete(product).onSuccess {
//            getItemCount()
//            loadProductByOffset()
//        }.onFailure {
//            _errorHandler.value = ErrorEventState(CART_DELETE_ERROR)
//        }
    }

    override fun onNext() {
        if (offSet == maxOffset) return
        offSet++
        loadProductByOffset()
    }

    override fun onPrevious() {
        if (offSet == 0) return
        offSet--
        loadProductByOffset()
    }

    companion object {
        const val CART_LOAD_ERROR = "LOAD ERROR"
        const val CART_DELETE_ERROR = "DELETE ERROR"
        const val PAGE_SIZE = 5
        const val PAGE_UPPER_BOUND = 4
    }
}
