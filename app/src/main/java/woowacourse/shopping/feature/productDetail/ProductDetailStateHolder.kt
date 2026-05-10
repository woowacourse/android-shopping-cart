package woowacourse.shopping.feature.productDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.productDetail.model.ProductInfo
import woowacourse.shopping.feature.productDetail.model.toUiModel
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductDetailStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productId: String,
    private val isFromRecent: Boolean,
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var product: Product? = null

    var productInfo: ProductInfo? by mutableStateOf(null)
        private set

    var previousProduct: Product? by mutableStateOf(null)
        private set

    val shouldShowRecentSummary: Boolean
        get() = !isFromRecent && previousProduct != null

    init {
        scope.launch {
            product = withContext(Dispatchers.IO) { productRepository.getProduct(productId) }
            refreshUiState()
            saveRecentProduct()
            observeRecentProducts()
        }
    }

    private fun observeRecentProducts() {
        recentProductRepository.getRecentProducts()
            .onEach { items ->
                previousProduct = items.firstOrNull { it.id != productId }
            }
            .launchIn(scope)
    }

    private fun saveRecentProduct() {
        val product = product ?: return
        scope.launch(Dispatchers.IO) {
            recentProductRepository.saveRecentProduct(product)
        }
    }

    fun onAddClick() {
        val product = product ?: return
        scope.launch(Dispatchers.IO) {
            cartRepository.updateCart(CartItem(product, Quantity(1)))
            refreshUiState()
        }
    }

    fun onIncreaseClick() {
        scope.launch(Dispatchers.IO) {
            val currentCartItem = cartRepository.getCartItem(productId).first() ?: return@launch
            cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value + 1)))
            refreshUiState()
        }
    }

    fun onDecreaseClick() {
        scope.launch(Dispatchers.IO) {
            val currentCartItem = cartRepository.getCartItem(productId).first() ?: return@launch
            if (currentCartItem.quantity.value <= 1) {
                cartRepository.deleteCartItem(productId)
            } else {
                cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value - 1)))
            }
            refreshUiState()
        }
    }

    private fun refreshUiState() {
        scope.launch {
            val quantity = withContext(Dispatchers.IO) {
                cartRepository.getCartItem(productId).first()?.quantity?.value ?: 0
            }
            productInfo = product?.toUiModel()?.copy(formattedQuantity = quantity.toString())
        }
    }
}

@Composable
fun retainProductDetailStateHolder(
    productId: String,
    isFromRecent: Boolean,
): ProductDetailStateHolder {
    val context = androidx.compose.ui.platform.LocalContext.current
    return retain(productId) {
        val application = context.applicationContext as woowacourse.shopping.ShoppingApplication
        ProductDetailStateHolder(
            ProductRepositoryImpl,
            CartRepositoryImpl(application.database.cartDao()),
            RecentProductRepositoryImpl(application.database.recentProductDao(), ProductRepositoryImpl),
            productId,
            isFromRecent
        )
    }
}

