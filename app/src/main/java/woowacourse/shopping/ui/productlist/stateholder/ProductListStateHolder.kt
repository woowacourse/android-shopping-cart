package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()

    var cart = Cart()
        private set

    var productUiModels by mutableStateOf(emptyList<ProductUiModel>())
        private set

    val cartUiModels: List<ProductUiModel>
        get() = cart.getProductList().map { toProductUiModel(it) }

    fun isEndList(): Boolean = _products.size >= MockData.MOCK_PRODUCTS.size

    suspend fun loadInitialProducts() {
        if (_products.isEmpty()) fetchProducts()
    }

    suspend fun fetchProducts() {
        if (isEndList()) return
        delay(500) // 비동기 상황 가정
        val toOffset = minOf(_products.size + PAGE_SIZE, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = _products.size,
                toIndex = toOffset,
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

    fun syncDeletedCartItems(deletedUiModels: List<ProductUiModel>) {
        this.cart = cart.filterById(deletedUiModels.map { it.id })
    }

    private fun toProductUiModel(product: Product): ProductUiModel = ProductUiModel.of(
        name = product.name,
        price = product.priceAmount(),
        imageUrl = product.imageUrl,
        id = product.id,
    )

    companion object {
        private const val PAGE_SIZE = 20
    }
}
