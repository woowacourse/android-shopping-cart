package woowacourse.shopping.viewmodel.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.view.cart.Cart

class ProductDetailViewModel(
    private val cart: Cart,
) : ViewModel() {
    fun addToCart(product: Product) {
        cart.add(product)
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
                        Cart,
                    ) as T
                }
            }
    }
}
