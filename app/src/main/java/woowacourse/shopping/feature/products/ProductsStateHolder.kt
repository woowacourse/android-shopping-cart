package woowacourse.shopping.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.products.model.ShoppingProductInfo
import woowacourse.shopping.feature.products.model.toUiModel

import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductsStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val recentProductRepository: RecentProductRepository,
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val totalCount = productRepository.getProductCount()
    private var pageCount = 0

    var products by mutableStateOf(emptyList<ShoppingProductInfo>().toImmutableList())
        private set

    var recentProducts by mutableStateOf(emptyList<ShoppingProductInfo>().toImmutableList())
        private set

    var isLastPage by mutableStateOf(false)
        private set

    var formattedCartItemCount by mutableStateOf("0")
        private set

    init {
        getProducts()

        cartRepository.getCartItems()
            .onEach { items ->
                refreshAllQuantities()
                formattedCartItemCount = items.size.toString()
            }
            .launchIn(scope)

        recentProductRepository.getRecentProducts()
            .onEach { items ->
                recentProducts = items.map { it.toUiModel() }.toImmutableList()
            }
            .launchIn(scope)
    }


    fun getProducts(pageSize: Int = 20) {
        scope.launch {
            val currentProducts = withContext(Dispatchers.IO) {
                productRepository
                    .getProducts(pageCount, pageSize)
                    .items
                    .map { product ->
                        val quantity = cartRepository.getCartItem(product.id).first()?.quantity?.value ?: 0
                        product.toUiModel().copy(formattedQuantity = quantity.toString())
                    }
                    .toImmutableList()
            }

            if (currentProducts.isNotEmpty()) {
                products = (products + currentProducts).toImmutableList()
                pageCount++
            }

            isLastPage = products.size >= totalCount
        }
    }

    fun onAddClick(productId: String) {
        val product = productRepository.getProduct(productId) ?: return
        scope.launch(Dispatchers.IO) {
            cartRepository.updateCart(CartItem(product, Quantity(1)))
        }
    }

    fun onIncreaseClick(productId: String) {
        scope.launch(Dispatchers.IO) {
            cartRepository.increaseCartItemQuantity(productId)
        }
    }

    fun onDecreaseClick(productId: String) {
        scope.launch(Dispatchers.IO) {
            val currentCartItem = cartRepository.getCartItem(productId).first() ?: return@launch
            if (currentCartItem.quantity.value <= 1) {
                cartRepository.deleteCartItem(productId)
            } else {
                cartRepository.decreaseCartItemQuantity(productId)
            }
        }
    }

    fun refreshAllQuantities() {
        scope.launch {
            products = withContext(Dispatchers.IO) {
                products.map { product ->
                    val quantity = cartRepository.getCartItem(product.id).first()?.quantity?.value ?: 0
                    product.copy(formattedQuantity = quantity.toString())
                }.toImmutableList()
            }
        }
    }
}

@Composable
fun retainProductsStateHolder(): ProductsStateHolder {
    val context = androidx.compose.ui.platform.LocalContext.current
    return retain {
        val application = context.applicationContext as woowacourse.shopping.ShoppingApplication
        ProductsStateHolder(
            ProductRepositoryImpl,
            woowacourse.shopping.data.repository.CartRepositoryImpl(application.database.cartDao()),
            RecentProductRepositoryImpl(application.database.recentProductDao(), ProductRepositoryImpl)
        )
    }
}
