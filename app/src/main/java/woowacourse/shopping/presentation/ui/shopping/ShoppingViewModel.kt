package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.UiState

class ShoppingViewModel(private val repository: ProductRepository = DummyProductRepository()) :
    ViewModel() {
    private var offSet: Int = 0

    private val _products = MutableLiveData<UiState<List<Product>>>(UiState.None)
    val products get() = _products

    fun loadProductByOffset() {
        repository.findByPaging(offSet, 20).onSuccess {
            if (_products.value is UiState.None || _products.value is UiState.Error) {
                _products.value = UiState.Finish(it)
            } else {
                _products.value = UiState.Finish((_products.value as UiState.Finish).data + it)
            }
            offSet++
        }.onFailure {
            _products.value = UiState.Error(LOAD_ERROR)
        }
    }

    companion object {
        const val LOAD_ERROR = "아이템을 끝까지 불러왔습니다"
    }
}
