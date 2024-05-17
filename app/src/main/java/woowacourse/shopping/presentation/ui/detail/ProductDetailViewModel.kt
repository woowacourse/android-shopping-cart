package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.ProductCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.ErrorEventState
import woowacourse.shopping.presentation.ui.UiState

class ProductDetailViewModel(private val productRepository: ProductRepository, private val productCartRepository: ProductCartRepository) : ViewModel() {
    private val _products = MutableLiveData<UiState<Product>>(UiState.None)
    val products: LiveData<UiState<Product>> get() = _products

    private val _errorHandler = MutableLiveData<ErrorEventState<String>>()
    val errorHandler: LiveData<ErrorEventState<String>> get() = _errorHandler

    fun loadById(productId: Long) {
        productRepository.findById(productId).onSuccess {
            _products.value = UiState.Success(it)
        }.onFailure {
            _errorHandler.value = ErrorEventState(PRODUCT_NOT_FOUND)
        }
    }

    fun saveCartItem(product: Product) {
        productCartRepository.save(product)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND"
    }
}
