package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.product.ShoppingUiModel
import woowacourse.shopping.presentation.shopping.toShoppingUiModel
import woowacourse.shopping.presentation.util.SingleLiveEvent

class ProductDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
    private val productId: Long,
) : ViewModel() {
    private val _product = MutableLiveData<ShoppingUiModel.Product>()
    val product: LiveData<ShoppingUiModel.Product> get() = _product

    private val _isAddedCart = SingleLiveEvent<Boolean>()
    val isAddedCart: LiveData<Boolean> get() = _isAddedCart

    init {
        loadProduct()
    }

    private fun loadProduct() {
        val product = shoppingRepository.productById(productId)?.toShoppingUiModel(true) ?: return
        _product.value = product
    }

    fun addCartProduct() {
        val product = _product.value ?: return
        cartRepository.addCartProduct(product.id, 1)
        _isAddedCart.value = true
    }

    fun increaseCount() {
        val product = _product.value ?: return
        val newCount = product.count + 1
        _product.value = product.copy(count = newCount)
        cartRepository.addCartProduct(product.id, newCount)
    }

    fun decreaseCount() {
        val product = _product.value ?: return
        val newCount = (product.count - 1).coerceAtLeast(0)
        _product.value = product.copy(count = newCount)
        cartRepository.addCartProduct(product.id, newCount)
    }

    companion object {
        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository,
            productId: Long,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    shoppingRepository,
                    cartRepository,
                    productId,
                )
            }
        }
    }
}
