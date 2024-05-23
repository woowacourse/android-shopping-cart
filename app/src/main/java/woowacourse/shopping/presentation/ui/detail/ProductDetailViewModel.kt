package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<UiState<Product>>(UiState.None)
    val products: LiveData<UiState<Product>> get() = _products

    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _addCartEvent = MutableLiveData<Event<Long>>()
    val addCartEvent: LiveData<Event<Long>>
        get() = _addCartEvent

    fun loadById(productId: Long) {
        productRepository.loadById(productId).onSuccess {
            _error.value = false
            _products.value = UiState.Success(it)
        }.onFailure {
            _error.value = true
        }
    }

    fun saveCartItem(
        product: Product,
        quantityDelta: Int,
    ) {
        cartRepository.updateQuantity(product, quantityDelta).onSuccess {
            _addCartEvent.value = Event(product.id)
        }
    }

    companion object {
        const val PRODUCT_NOT_FOUND = "PRODUCT NOT FOUND"
    }
}
