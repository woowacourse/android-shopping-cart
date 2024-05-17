package woowacourse.shopping.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartDao
import woowacourse.shopping.model.data.ProductDao
import woowacourse.shopping.ui.state.UiState

class ProductDetailViewModel(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
) : ViewModel() {
    private val _uiState: MutableLiveData<UiState<Product>> = MutableLiveData(UiState.LOADING)
    val uiState: LiveData<UiState<Product>> get() = _uiState

    fun loadProduct(productId: Long) {
        runCatching { productDao.find(-1L) }
            .onSuccess {
                _uiState.value = UiState.SUCCESS(it)
            }.onFailure {
                _uiState.value = UiState.ERROR(it)
            }
    }

    fun addProductToCart() {
        _uiState.value?.let { cartDao.save((it as UiState.SUCCESS).data) }
    }
}
