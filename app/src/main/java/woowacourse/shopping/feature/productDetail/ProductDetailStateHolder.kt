package woowacourse.shopping.feature.productDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.feature.productDetail.model.ProductInfo
import woowacourse.shopping.feature.productDetail.model.toUiModel

class ProductDetailStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productId: String,
    private val isFromRecent: Boolean,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob()),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private var product: Product? = null
    private var selectedQuantity by mutableIntStateOf(1)

    var productInfo: ProductInfo? by mutableStateOf(null)
        private set

    var previousProduct: Product? by mutableStateOf(null)
        private set

    val shouldShowRecentSummary: Boolean
        get() = !isFromRecent && previousProduct != null

    init {
        scope.launch {
            product = withContext(ioDispatcher) { productRepository.getProduct(productId) }
            updateProductInfo()
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
        scope.launch(ioDispatcher) {
            recentProductRepository.saveRecentProduct(product)
        }
    }

    fun onAddClick() {
        val product = product ?: return
        val quantityToAdd = selectedQuantity
        scope.launch(ioDispatcher) {
            val currentCartItem = cartRepository.getCartItem(productId).first()
            val nextQuantity = (currentCartItem?.quantity?.value ?: 0) + quantityToAdd
            cartRepository.updateCart(CartItem(product, Quantity(nextQuantity)))
        }
    }

    fun onIncreaseClick() {
        selectedQuantity += 1
        updateProductInfo()
    }

    fun onDecreaseClick() {
        if (selectedQuantity <= 1) return
        selectedQuantity -= 1
        updateProductInfo()
    }

    private fun updateProductInfo() {
        productInfo = product?.toUiModel()?.copy(formattedQuantity = selectedQuantity.toString())
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
