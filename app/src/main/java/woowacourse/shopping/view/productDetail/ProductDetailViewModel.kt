package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.MutableSingleLiveData
import woowacourse.shopping.view.SingleLiveData

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    val imageUrl: LiveData<String?> = _product.map { it.imageUrl }

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    private val _event: MutableSingleLiveData<ProductDetailEvent> = MutableSingleLiveData()
    val event: SingleLiveData<ProductDetailEvent> get() = _event

    fun updateProduct(product: Product) {
        _product.value = product
        updateQuantity()
    }

    fun updateQuantity() {
        val product = product.value ?: return
        shoppingCartRepository.quantityOf(product) { result: Result<Int> ->
            if (result.getOrNull() != 0) {
                _quantity.postValue(result.getOrElse { 1 })
            }
        }
    }

    fun addToShoppingCart() {
        val product: Product =
            product.value ?: run {
                _event.setValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                return
            }
        val cartItem = CartItem(product, quantity.value ?: 1)

        shoppingCartRepository.add(cartItem) { result: Result<Unit> ->
            result
                .onSuccess {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
                }.onFailure {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                }
        }
    }

    fun plusProductQuantity() {
        _quantity.value = quantity.value?.plus(1) ?: 2
    }

    fun minusProductQuantity() {
        _quantity.value = quantity.value?.minus(1)?.coerceAtLeast(1) ?: 1
    }
}
