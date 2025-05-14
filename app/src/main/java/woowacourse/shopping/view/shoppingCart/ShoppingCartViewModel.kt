package woowacourse.shopping.view.shoppingCart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _shoppingCart: MutableLiveData<List<Product>> = MutableLiveData()
    val shoppingCart: LiveData<List<Product>> get() = _shoppingCart

    private val _event: MutableLiveData<ShoppingCartEvent> = MutableLiveData()
    val event: LiveData<ShoppingCartEvent> get() = _event

    fun updateShoppingCart() {
        thread {
            runCatching {
                shoppingCartRepository.products
            }.onSuccess { products: List<Product> ->
                _shoppingCart.postValue(products)
            }.onFailure {
                _event.postValue(ShoppingCartEvent.UPDATE_SHOPPING_CART_FAILURE)
            }
        }
    }
}
