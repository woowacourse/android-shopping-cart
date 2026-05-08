package woowacourse.shopping.features.productList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import woowacourse.shopping.data.DataProvider
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItemQuantity
import woowacourse.shopping.domain.cart.repository.CartRepository
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName
import woowacourse.shopping.domain.product.repository.ProductRepository

data class ProductUiState(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int,
    val isExistProductToCart: Boolean,
)

class ProductListStateHolder(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    val totalProductCount = productRepository.getProductsSize()
    var pageCount = 0
    var isLastPage by mutableStateOf(false)
    var totalCartItemCount by mutableStateOf(0)
    var productUiList by mutableStateOf(emptyList<ProductUiState>())

    init {
        moreProducts()
    }

    fun moreProducts() {
        val moreProducts =
            productRepository.getPagedProducts(page = pageCount, pageSize = PAGE_SIZE).map {
                ProductUiState(
                    id = it.id,
                    name = it.name.value,
                    price = it.price.value,
                    imageUrl = it.imageUrl.value,
                    quantity = cartRepository.getQuantity(CartItem(it, quantity = CartItemQuantity(0))),
                    isExistProductToCart = cartRepository.isCartItemExist(CartItem(it, quantity = CartItemQuantity(0))),
                )
            }
        if (moreProducts.isEmpty()) return
        productUiList = productUiList + moreProducts
        pageCount += 1
        isLastPage = productUiList.size >= totalProductCount
    }

    fun loadProductUiList() {
        totalCartItemCount = cartRepository.getTotalCartItemCount()
        productUiList =
            productUiList.map {
                ProductUiState(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    quantity = cartRepository.getQuantity(CartItem(toProductUi(it), quantity = CartItemQuantity(0))),
                    isExistProductToCart = cartRepository.isCartItemExist(CartItem(toProductUi(it), quantity = CartItemQuantity(0))),
                )
            }
    }

    fun toProductUi(productUiState: ProductUiState): Product =
        Product(
            id = productUiState.id,
            name = ProductName(productUiState.name),
            price = Price(productUiState.price),
            imageUrl = ImageUrl(productUiState.imageUrl),
        )

    fun addCartItem(productUi: ProductUiState) {
        cartRepository.addCartItem(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0)), 1)
        loadProductUiList()
    }

    fun minusCartItem(productUi: ProductUiState) {
        val cartItem = CartItem(toProductUi(productUi), quantity = CartItemQuantity(0))
        val quantity = cartRepository.getQuantity(cartItem)
        if (quantity == 1) {
            cartRepository.removeCartItem(cartItem)
        } else {
            cartRepository.minusCartItem(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0)), 1)
        }
        loadProductUiList()
    }

    fun removeCartItem(productUi: ProductUiState) {
        cartRepository.removeCartItem(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0)))
        loadProductUiList()
    }

    fun getQuantity(productUi: ProductUiState): Int {
        val productQuantity = cartRepository.getQuantity(CartItem(toProductUi(productUi), quantity = CartItemQuantity(0)))
        return productQuantity
    }

    fun isExistProduct(productUi: ProductUiState): Boolean = productUi.quantity > 0

    fun isHasProductId(productId: String): Boolean = productRepository.isProductExist(productId)

    companion object {
        const val PAGE_SIZE = 20
    }
}

@Composable
fun retainProductListStateHolder(): ProductListStateHolder =
    retain {
        ProductListStateHolder(
            productRepository = DataProvider.productRepository,
            cartRepository = DataProvider.cartRepository,
        )
    }
