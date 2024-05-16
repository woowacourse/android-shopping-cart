package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toUiModel

class ProductDetailViewModel(
    private val shoppingRepository: ShoppingRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _product = MutableLiveData<ProductUi>()
    val product: LiveData<ProductUi> get() = _product

    private val _isAddedCart = MutableLiveData<Boolean>()
    val isAddedCart: LiveData<Boolean> get() = _isAddedCart

    fun loadProduct(id: Long) {
        val product = shoppingRepository.productById(id)
        if (product != null) {
            _product.value = product.toUiModel()
        }
    }

    fun addCartProduct() {
        val product = _product.value
        if (product != null) {
            cartRepository.addCartProduct(product.id)
            _isAddedCart.value = true
        }
    }

    fun addCartDone() {
        _isAddedCart.value = false
    }

    companion object {
        fun factory(
            shoppingRepository: ShoppingRepository,
            cartRepository: CartRepository
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    shoppingRepository,
                    cartRepository
                )
            }
        }
    }
}
