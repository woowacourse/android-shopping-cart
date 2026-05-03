package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.delay
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    var cart = Cart()
        private set

    var uiModels = emptyList<ProductUiModel>()

    fun isEndList(): Boolean = _products.size >= MockData.MOCK_PRODUCTS.size

    suspend fun fetchProducts(): List<Product> {
        delay(500) // 비동기 상황 가정
        val toOffset = minOf(_products.size + PAGE_SIZE, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = _products.size,
                toIndex = toOffset,
            ),
        )

        return products
    }

    fun addCartItem(addedId: String): Boolean {
        val targetProduct = _products.firstOrNull { it.id == addedId } ?: return false
        val newCartItem = CartItem(
            product = targetProduct,
            quantity = Quantity(1),
        )

        this.cart = cart.plusCartItem(newCartItem)
        this.uiModels = toProductUiModels()
        return true
    }

    fun syncDeletedCartItems(deletedUiModels: List<ProductUiModel>) {
        this.cart = cart.filterById(deletedUiModels.map { it.id })
        this.uiModels = toProductUiModels()
    }

    fun toProductUiModel(product: Product): ProductUiModel = ProductUiModel.of(
        name = product.name,
        price = product.priceAmount(),
        imageUrl = product.imageUrl,
        id = product.id,
    )

    private fun toProductUiModels(): List<ProductUiModel> = cart.getProductList().map { toProductUiModel(it) }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
