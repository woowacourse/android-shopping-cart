package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class ProductDetailViewModel : ViewModel() {
    private val repository = DummyProductRepository()

    private val _products = MutableLiveData<UiState<Product>>(UiState.None)
    val products: LiveData<UiState<Product>> get() = _products

    fun loadById(productId: Long) {
        repository.loadById(productId).onSuccess {
            _products.value = UiState.Finish(it)
        }.onFailure {
            _products.value = UiState.Error("ERROR")
        }
    }

    fun saveCartItem(productId: Long) {
        DummyCartRepository.addData(productId)
    }
}
