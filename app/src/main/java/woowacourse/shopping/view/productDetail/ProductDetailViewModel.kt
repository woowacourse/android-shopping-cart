package woowacourse.shopping.view.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository
import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.MutableSingleLiveData
import woowacourse.shopping.view.common.SingleLiveData

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository = DefaultShoppingCartRepository(),
) : ViewModel() {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _event: MutableSingleLiveData<ProductDetailEvent> =
        MutableSingleLiveData()
    val event: SingleLiveData<ProductDetailEvent> get() = _event

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    private val _price: MutableLiveData<Int> = MutableLiveData()
    val price: LiveData<Int> get() = _price

    fun updateProduct(product: Product) {
        _product.value = product
        _price.value = product.price
    }

    fun addToShoppingCart() {
        val product = requireNotNull(product.value) { "product.value가 null입니다." }
        shoppingCartRepository.addWithQuantity(product, quantity.value ?: return) { result ->
            result
                .onSuccess {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_SUCCESS)
                }.onFailure {
                    _event.postValue(ProductDetailEvent.ADD_SHOPPING_CART_FAILURE)
                }
        }
    }

    fun plusQuantity() {
        _quantity.value = (_quantity.value ?: 0) + 1
        _price.value = quantity.value?.times(product.value?.price ?: 0)
    }

    fun minusQuantity() {
        _quantity.value = (_quantity.value)?.minus(1)?.coerceAtLeast(1)
        _price.value = quantity.value?.times(product.value?.price ?: 0)
    }
}
