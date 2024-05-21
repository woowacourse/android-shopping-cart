package woowacourse.shopping.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.common.Event
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product
import woowacourse.shopping.ui.utils.AddCartQuantityBundle

class ProductDetailViewModel(
    private val productId: Long,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _productLoadError = MutableLiveData<Event<Boolean>>()
    val productLoadError: LiveData<Event<Boolean>> get() = _productLoadError

    private val _isSuccessAddCart = MutableLiveData<Event<Boolean>>()
    val isSuccessAddCart: LiveData<Event<Boolean>> get() = _isSuccessAddCart

    val productTotalPrice: LiveData<Int> = _product.map { it.totalPrice() }

    val addCartQuantityBundle: LiveData<AddCartQuantityBundle> =
        _product.map {
            AddCartQuantityBundle(
                product = it,
                onIncreaseProductQuantity = { increaseQuantity() },
                onDecreaseProductQuantity = { decreaseQuantity() },
            )
        }

    init {
        loadProduct()
    }

    private fun loadProduct() {
        runCatching {
            productRepository.find(productId)
        }.onSuccess {
            _product.value = it
            _productLoadError.value = Event(false)
        }.onFailure {
            _productLoadError.value = Event(true)
        }
    }

    fun addCartProduct() {
        runCatching {
            val product = _product.value ?: return
            cartRepository.changeQuantity(product, product.quantity)
        }.onSuccess {
            _isSuccessAddCart.value = Event(true)
        }.onFailure {
            _isSuccessAddCart.value = Event(false)
        }
    }

    private fun increaseQuantity() {
        var quantity = _product.value?.quantity ?: return
        _product.value = _product.value?.copy(quantity = ++quantity)
    }

    private fun decreaseQuantity() {
        var quantity = _product.value?.quantity ?: return
        _product.value = _product.value?.copy(quantity = --quantity)
    }
}
