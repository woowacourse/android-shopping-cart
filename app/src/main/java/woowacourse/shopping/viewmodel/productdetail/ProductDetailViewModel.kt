package woowacourse.shopping.viewmodel.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.CartRepositoryImpl

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
) : ViewModel() {
    fun addToCart(product: Product) {
        cartRepository.addProduct(product)
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
                        CartRepositoryImpl.getInstance(),
                    ) as T
                }
            }
    }
}
