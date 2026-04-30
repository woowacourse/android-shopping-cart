package woowacourse.shopping.ui.productlist.stateholder

import androidx.compose.runtime.mutableStateListOf
import woowacourse.shopping.constants.MockData
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ui.state.ProductUiModel

class ProductListStateHolder {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    var cart = Cart()

    // 60 45 currentPage
    // 20 20 1
    // 40 40 2
    // 60 45 3
    // 끝

    fun isEndList(): Boolean {
        return _products.size >= MockData.MOCK_PRODUCTS.size
    }

    fun fetchProducts(): List<Product> {
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
        return Cart(
            cart.cartItems.filter { cartItem ->
                uiModels.any { it.id == cartItem.product.id }
            },
        )
    }

    fun toProductUiModels(): List<ProductUiModel> {
        return cart.cartItems.map { toProductUiModel(it.product) }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
