package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.delay
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    var cart = Cart()

    fun isEndList(): Boolean {
        return _products.size >= MockData.MOCK_PRODUCTS.size
    }

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

    fun toProductUiModel(product: Product): ProductUiModel {
        return ProductUiModel.of(
            name = product.name,
            price = product.priceAmount(),
            imageUrl = product.imageUrl,
            id = product.id,
        )
    }

    fun addCartItem(cartItem: CartItem): Cart {
        return cart.plusCartItem(cartItem)
    }

    fun replaceCartItems(uiModels: List<ProductUiModel>): Cart {
        return cart.filterById(uiModels.map { it.id })
    }

    fun toProductUiModels(): List<ProductUiModel> {
        return cart.getProductList().map { toProductUiModel(it) }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
