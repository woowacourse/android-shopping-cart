package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.state.ProductUiModel

@Composable
fun rememberProductListStateHolder(): ProductListStateHolder = rememberSaveable(saver = ProductListStateHolder.Saver()) {
    ProductListStateHolder()
}

class ProductListStateHolder(initialPage: Int = 0) {
    private val _products = mutableStateListOf<Product>()

    var cart = Cart()
        private set

    var productUiModels by mutableStateOf(emptyList<ProductUiModel>())
        private set

    val cartUiModels: List<ProductUiModel>
        get() = cart.getProductList().map { toProductUiModel(it) }

    private var currentPage = initialPage

    fun isEndList(): Boolean = _products.size >= MockData.MOCK_PRODUCTS.size

    fun loadInitialProducts() {
        if (_products.isEmpty()) {
            if (currentPage > 0) {
                restoreProducts(currentPage)
                return
            }
            fetchProducts()
        }
    }

    fun fetchProducts(pageSize: Int = PAGE_SIZE) {
        if (isEndList()) return

        val fromIndex = currentPage * pageSize
        val toIndex = minOf(fromIndex + pageSize, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = fromIndex,
                toIndex = toIndex,
            ),
        )
        currentPage++
        syncUiModels()
    }

    private fun restoreProducts(targetPage: Int) {
        val totalItemsSize = targetPage * PAGE_SIZE
        val toIndex = minOf(totalItemsSize, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = 0,
                toIndex = toIndex,
            ),
        )
        syncUiModels()
    }

    private fun syncUiModels() {
        this.productUiModels = _products.map { toProductUiModel(it) }
    }

    fun addCartItem(addedId: String): Boolean {
        val targetProduct = _products.firstOrNull { it.id == addedId } ?: return false
        val newCartItem = CartItem(
            product = targetProduct,
            quantity = Quantity(1),
        )

        this.cart = cart.plusCartItem(newCartItem)
        return true
    }

    fun removeCartItems(deletedIds: List<String>) {
        this.cart = cart.removeCartItems(deletedIds)
    }

    private fun toProductUiModel(product: Product): ProductUiModel = ProductUiModel.of(
        name = product.name,
        price = product.price.amount,
        imageUrl = product.imageUrl,
        id = product.id,
    )

    companion object {
        private const val PAGE_SIZE = 20

        fun Saver(): Saver<ProductListStateHolder, Any> = listSaver(
            save = { stateHolder ->
                listOf(
                    stateHolder.currentPage,
                )
            },
            restore = { savedList ->
                val savedPage = savedList[0]

                ProductListStateHolder(savedPage).apply {
                }
            },
        )
    }
}
