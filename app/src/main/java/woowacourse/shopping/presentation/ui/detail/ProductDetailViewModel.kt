package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyCartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<UiState<Product>>(UiState.None)
    val products: LiveData<UiState<Product>> get() = _products

    fun loadById(productId: Long) {
        repository.loadById(productId).onSuccess {
            _products.value = UiState.Finish(it)
        }.onFailure {
            _products.value = UiState.Error("ERROR")
        }
    }

    fun saveCartItem(product: Product) {
        DummyCartRepository.addData(product)
    }
}
