package woowacourse.shopping.presentation.productdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    var amount by mutableStateOf(1)
        private set

    fun increaseQuantity() {
        amount++
    }

    fun decreaseQuantity() {
        if (amount == 1) return
        amount--
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addToCart(productId: Uuid) {
        val product = productRepository.findProductById(productId) ?: return
        cartRepository.increaseQuantity(product, amount)
    }
}

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(
                productRepository = productRepository,
                cartRepository = cartRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
