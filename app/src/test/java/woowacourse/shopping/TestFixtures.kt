package woowacourse.shopping

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel

val imageUrl = "https://www.naver.com/"
val title = "올리브"
val price = 1500

fun product(id: Long) = Product(id, imageUrl, title, price)

fun products(size: Int): List<Product> {
    return List(size) { product(it.toLong()) }
}

fun cartItem(
    id: Long,
    quantity: Quantity = Quantity(),
): CartItem {
    return CartItem(id, id, quantity)
}

fun cartItems(size: Int): List<CartItem> {
    return List(size) { cartItem(it.toLong()) }
}

fun convertProductUiModel(
    cartItems: List<CartItem>,
    products: List<Product>,
): List<ProductUiModel> {
    return products.map { product ->
        val cartItem = cartItems.firstOrNull { it.productId == product.id }
        if (cartItem == null) {
            ProductUiModel.from(product)
        } else {
            ProductUiModel.from(product, cartItem.quantity)
        }
    }
}

fun convertProductUiModel(
    products: List<Product>,
    cartRepository: CartRepository,
): List<ProductUiModel> {
    return products.map { product ->
        runCatching { cartRepository.find(product.id) }
            .map { ProductUiModel.from(product, it.quantity) }
            .getOrElse { ProductUiModel.from(product) }
    }
}
