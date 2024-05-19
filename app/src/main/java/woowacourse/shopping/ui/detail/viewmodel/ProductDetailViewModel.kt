package woowacourse.shopping.ui.detail.viewmodel

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
    private val _productDetailLoadState: MutableLiveData<UiState<Product>> = MutableLiveData(UiState.LOADING)
    val productDetailLoadState: LiveData<UiState<Product>> get() = _productDetailLoadState

    fun loadProduct(productId: Long) {
        runCatching { productDao.find(productId) }
            .onSuccess {
                _productDetailLoadState.value = UiState.SUCCESS(it)
            }.onFailure {
                _productDetailLoadState.value = UiState.ERROR(it)
            }
    }

    fun addProductToCart() {
        _productDetailLoadState.value?.let { cartDao.save((it as UiState.SUCCESS).data) }
    }
}
