package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.detail.DetailScreenEvent

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _event = MutableLiveData<DetailScreenEvent>()
    val event: LiveData<DetailScreenEvent> get() = _event

    fun load(productId: Long) {
        _product.value = productRepository[productId]
    }

    fun addProduct() {
        product.value?.let {
            cartRepository.insert(it)
            _event.value = DetailScreenEvent.MoveToCart
        }
    }
}
