package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.ShoppingCartRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    val product: Product,
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    fun addToShoppingCart() {
        repository.insert(product.id)
        _navigateEvent.value = Unit
    }
}
