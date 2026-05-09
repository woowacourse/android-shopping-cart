package woowacourse.shopping.feature.productDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.productDetail.model.ProductInfo
import woowacourse.shopping.feature.productDetail.model.toUiModel

class ProductDetailStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productId: String,
) {
    private val product: Product? = productRepository.getProduct(productId)

    var productInfo: ProductInfo? by mutableStateOf(null)
        private set

    init {
        refreshUiState()
    }

    fun onAddClick() {
        val product = product ?: return
        cartRepository.updateCart(CartItem(product, Quantity(1)))
        refreshUiState()
    }

    fun onIncreaseClick() {
        val currentCartItem = cartRepository.getCartItem(productId) ?: return
        cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value + 1)))
        refreshUiState()
    }

    fun onDecreaseClick() {
        val currentCartItem = cartRepository.getCartItem(productId) ?: return
        if (currentCartItem.quantity.value <= 1) {
            cartRepository.deleteCartItem(productId)
        } else {
            cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value - 1)))
        }
        refreshUiState()
    }

    private fun refreshUiState() {
        val quantity = cartRepository.getCartItem(productId)?.quantity?.value ?: 0
        productInfo = product?.toUiModel()?.copy(formattedQuantity = quantity.toString())
    }
}

@Composable
fun retainProductDetailStateHolder(productId: String): ProductDetailStateHolder =
    retain(productId) {
        ProductDetailStateHolder(ProductRepositoryImpl, CartRepositoryImpl, productId)
    }
