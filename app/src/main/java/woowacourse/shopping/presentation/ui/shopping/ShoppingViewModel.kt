package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.ErrorEventState
import woowacourse.shopping.presentation.ui.UiState

class ShoppingViewModel(private val repository: ProductRepository = DummyProductRepository()) :
    ViewModel() {
    private var offSet: Int = 0

    private val _products = MutableLiveData<UiState<List<Product>>>(UiState.None)
    val products get() = _products

    private val _errorHandler = MutableLiveData<ErrorEventState<String>>()
    val errorHandler: LiveData<ErrorEventState<String>> get() = _errorHandler

    fun loadProductByOffset() {
        repository.findByPaging(offSet, PAGE_SIZE).onSuccess {
            if (_products.value is UiState.None) {
                _products.value = UiState.Success(it)
            } else {
                _products.value = UiState.Success((_products.value as UiState.Success).data + it)
            }
            offSet++
        }.onFailure {
            _errorHandler.value = ErrorEventState(LOAD_ERROR)
        }
    }

    companion object {
        const val LOAD_ERROR = "아이템을 끝까지 불러왔습니다"
        const val PAGE_SIZE = 20
    }
}
