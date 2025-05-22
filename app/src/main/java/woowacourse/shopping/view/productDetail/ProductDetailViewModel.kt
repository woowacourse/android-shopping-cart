package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _cartItem: MutableLiveData<CartItem> = MutableLiveData()
    val cartItem: LiveData<CartItem> get() = _cartItem

    val imageUrl: LiveData<String?> = _cartItem.map { it.imageUrl }

    private val _event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductDetailEvent> get() = _event

    fun updateProduct(cartItem: CartItem) {
        _cartItem.value = cartItem
    }

    fun addToShoppingCart() {
        val cartItem: CartItem =
            cartItem.value ?: run {
                _event.setValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                return
            }

        shoppingCartRepository.add(cartItem) { result: Result<Unit> ->
            result
                .onSuccess {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
                }.onFailure {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                }
        }
    }
}
