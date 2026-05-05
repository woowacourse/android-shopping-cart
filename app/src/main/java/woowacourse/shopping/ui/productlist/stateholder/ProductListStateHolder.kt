package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    var cart = Cart(emptyList())

    var uiModels = emptyList<ProductUiModel>()

    var isLoading by mutableStateOf(false)
        private set

    fun isEndList(): Boolean {
        return _products.size >= MockData.MOCK_PRODUCTS.size
    }

    suspend fun loadingFetch() {
        isLoading = true
        try {
            uiModels = fetchProducts(pageSize = 20)
                .map(this::toProductUiModel)
        } finally {
            isLoading = false
        }
    }

    suspend fun fetchProducts(pageSize: Int): List<Product> {
        delay(2000) // 비동기 상황 가정
        val toOffset = minOf(_products.size + pageSize, MockData.MOCK_PRODUCTS.size)

        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = _products.size,
                toIndex = toOffset,
            ),
        )

        return products
    }

    fun toProductUiModel(product: Product): ProductUiModel {
        return ProductUiModel.of(
            name = product.name,
            price = product.priceAmount(),
            imageUrl = product.imageUrl,
            id = product.id,
        )
    }

    fun addCartItem(cartContent: CartContent): Cart {
        return cart.plusCartContent(cartContent)
    }

    fun replaceCartItems(ids: List<String>): Cart {
        return cart.retainOnly(ids)
    }

    fun toProductUiModels(): List<ProductUiModel> {
        return cart.getProductList().map { toProductUiModel(it) }
    }
}
