package woowacourse.shopping.view.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.Cart
import woowacourse.shopping.view.cart.CartImpl

class ProductDetailViewModel(
    private val cart: Cart,
) : ViewModel() {
    private val _addToCart = MutableLiveData<Unit>()
    val addToCart: LiveData<Unit> = _addToCart

    private val _closeProductDetail = MutableLiveData<Unit>()
    val closeProductDetail: LiveData<Unit> = _closeProductDetail

    fun onCloseClicked() {
        _closeProductDetail.value = Unit
    }

    fun onAddToCartClicked(product: Product) {
        cart.add(product)
        _addToCart.value = Unit
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    checkNotNull(extras[APPLICATION_KEY])
                    extras.createSavedStateHandle()

                    return ProductDetailViewModel(
                        CartImpl,
                    ) as T
                }
            }
    }
}
