package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    private val product: Product,
    private val repository: CartProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    override fun onProductAddClick() {
        repository.insert(product.id)
        _navigateEvent.value = Unit
    }
}
