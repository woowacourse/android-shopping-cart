package woowacourse.shopping.ui.productlist.stateholder

import android.nfc.tech.MifareUltralight.PAGE_SIZE
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

    fun getAllProducts(): List<Product> {
        if (_products.size % PAGE_SIZE != 0) {
            return products // 더 이상 추가할 상품이 없을 때
        }
        _products.addAll(
            MockData.MOCK_PRODUCTS.subList(
                fromIndex = _products.size,
                toIndex = _products.size + PAGE_SIZE,
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
