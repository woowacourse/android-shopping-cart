package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CartProduct.Companion.EMPTY_CART_PRODUCT
import woowacourse.shopping.domain.repository.CartRepository

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _cartProduct: MutableLiveData<CartProduct> = MutableLiveData(EMPTY_CART_PRODUCT)
    val cartProduct: LiveData<CartProduct> get() = _cartProduct

    fun loadProductDetail(id: Int) {
        cartRepository.getCartProduct(id) { cartProduct ->
            _cartProduct.postValue(cartProduct)
        }
    }

    fun addCartProduct() {
        cartRepository.updateCartProduct(cartProduct.value ?: return)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY]) as ShoppingApp

                    return ProductDetailViewModel(
                        application.cartRepository,
                    ) as T
                }
            }
    }
}
