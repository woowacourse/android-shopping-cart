package woowacourse.shopping.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.feature.products.model.ShoppingProductInfo
import woowacourse.shopping.feature.products.model.toUiModel

class ProductsStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    private val totalCount = productRepository.getProductCount()
    private var pageCount = 0

    var products by mutableStateOf(emptyList<ShoppingProductInfo>().toImmutableList())
        private set

    var isLastPage by mutableStateOf(false)
        private set

    init {
        getProducts()
    }

    fun getProducts(pageSize: Int = 20) {
        val currentProducts =
            productRepository
                .getProducts(pageCount, pageSize)
                .items
                .map { product ->
                    val quantity = cartRepository.getCartItem(product.id)?.quantity?.value ?: 0
                    product.toUiModel().copy(formattedQuantity = quantity.toString())
                }
                .toImmutableList()

        if (currentProducts.isNotEmpty()) {
            products = (products + currentProducts).toImmutableList()
            pageCount++
        }

        isLastPage = products.size >= totalCount
    }

    fun onAddClick(productId: String) {
        val product = productRepository.getProduct(productId) ?: return
        cartRepository.updateCart(CartItem(product, Quantity(1)))
        updateProductQuantity(productId)
    }

    fun onIncreaseClick(productId: String) {
        val currentCartItem = cartRepository.getCartItem(productId) ?: return
        cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value + 1)))
        updateProductQuantity(productId)
    }

    fun onDecreaseClick(productId: String) {
        val currentCartItem = cartRepository.getCartItem(productId) ?: return
        if (currentCartItem.quantity.value <= 1) {
            cartRepository.deleteCartItem(productId)
        } else {
            cartRepository.updateCart(CartItem(currentCartItem.product, Quantity(currentCartItem.quantity.value - 1)))
        }
        updateProductQuantity(productId)
    }

    private fun updateProductQuantity(productId: String) {
        val quantity = cartRepository.getCartItem(productId)?.quantity?.value ?: 0
        products = products.map {
            if (it.id == productId) it.copy(formattedQuantity = quantity.toString()) else it
        }.toImmutableList()
    }
}

@Composable
fun retainProductsStateHolder(): ProductsStateHolder =
    retain {
        ProductsStateHolder(ProductRepositoryImpl, CartRepositoryImpl)
    }
