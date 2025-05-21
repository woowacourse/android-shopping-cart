package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    val imageUrl: LiveData<String> = _product.map { it.imageUrl }

    private val _event: MutableSingleLiveData<Event> = MutableSingleLiveData()
    val event: SingleLiveData<Event> get() = _event

    fun updateProduct(product: Product) {
        _product.value = product
    }

    fun addToShoppingCart() {
        val product: Product =
            product.value ?: run {
                _event.setValue(Event.ADD_SHOPPING_CART_FAILURE)
                return
            }

        shoppingCartRepository.add(product) { result: Result<Unit> ->
            result
                .onSuccess {
                    _event.postValue(Event.ADD_SHOPPING_CART_SUCCESS)
                }.onFailure {
                    _event.postValue(Event.ADD_SHOPPING_CART_FAILURE)
                }
        }
    }

    enum class Event {
        ADD_SHOPPING_CART_SUCCESS,
        ADD_SHOPPING_CART_FAILURE,
    }
}
