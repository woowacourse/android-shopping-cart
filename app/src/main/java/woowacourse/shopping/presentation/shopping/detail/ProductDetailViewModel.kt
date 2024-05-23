package woowacourse.shopping.presentation.shopping.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.cart.CartProductUi
import woowacourse.shopping.presentation.cart.toUiModel
import woowacourse.shopping.presentation.common.MutableSingleLiveData
import woowacourse.shopping.presentation.common.SingleLiveData
import woowacourse.shopping.presentation.shopping.toCartUiModel

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val _cartProduct = MutableLiveData<CartProductUi>()
    val cartProduct: LiveData<CartProductUi> get() = _cartProduct

    private val _isAddedCart = MutableSingleLiveData<Boolean>()
    val isAddedCart: SingleLiveData<Boolean> get() = _isAddedCart

    fun loadCartProduct(id: Long) {
        cartRepository.filterCarProducts(listOf(id)).onSuccess {
            if (it.isEmpty()) return loadProduct(id)
            _cartProduct.value = it.first().toUiModel()
        }.onFailure {
            // TODO Error handling
        }
    }

    private fun loadProduct(id: Long) {
        shoppingRepository.productById(id).onSuccess {
            _cartProduct.value = it.toCartUiModel()
        }.onFailure {
            // TODO Error handling
        }
    }

    fun increaseProductCount() {
        val cartProduct = _cartProduct.value ?: return
        cartRepository.updateCartProduct(cartProduct.product.id, cartProduct.count + 1).onSuccess {
            _cartProduct.value = cartProduct.copy(count = cartProduct.count + 1)
        }.onFailure {
            // TODO Error handling
        }
    }

    fun decreaseProductCount() {
        val cartProduct = _cartProduct.value ?: return
        if (cartProduct.count <= 0) return // TODO : Handle error
        cartRepository.updateCartProduct(cartProduct.product.id, cartProduct.count - 1).onSuccess {
            _cartProduct.value = cartProduct.copy(count = cartProduct.count - 1)
        }.onFailure {
            // TODO Error handling
        }
    }

    fun addCartProduct() {
        val cartProduct = _cartProduct.value ?: return
        // TODO CHANGE COUNT
        cartRepository.updateCartProduct(cartProduct.product.id, 1).onSuccess {
            _isAddedCart.setValue(true)
        }.onFailure {
            // TODO Error handling
        }
    }

    companion object {
        fun factory(
            cartRepository: CartRepository,
            shoppingRepository: ShoppingRepository
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory {
                ProductDetailViewModel(
                    cartRepository,
                    shoppingRepository
                )
            }
        }
    }
}
