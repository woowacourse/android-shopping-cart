package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Product
import woowacourse.shopping.presentation.ui.UiState

class ProductDetailViewModel(private val productRepository: ProductRepository, private val cartRepository: CartRepository) : ViewModel() {
    private val _products = MutableLiveData<UiState<Product>>(UiState.None)
    val products: LiveData<UiState<Product>> get() = _products

    fun loadById(productId: Long) {
        productRepository.loadById(productId).onSuccess {
            _products.value = UiState.Finish(it)
        }.onFailure {
            _products.value = UiState.Error("PRODUCT NOT FOUND")
        }
    }

    fun saveCartItem(product: Product) {
        cartRepository.addData(product)
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND"
    }
}
