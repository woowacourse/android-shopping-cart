package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toUiModel
import woowacourse.shopping.presentation.util.SingleLiveEvent

class ProductDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository,
    private val productId: Long,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUi>()
    val product: LiveData<ProductUi> get() = _product

    private val _isAddedCart = SingleLiveEvent<Boolean>()
    val isAddedCart: LiveData<Boolean> get() = _isAddedCart

    init {
        loadProduct()
    }

    private fun loadProduct() {
        val product = shoppingRepository.productById(productId) ?: return
        _product.value = product.toUiModel()
    }

    fun addCartProduct() {
        val product = _product.value ?: return
        cartRepository.addCartProduct(product.id)
        _isAddedCart.value = true
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
