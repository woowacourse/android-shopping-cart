package woowacourse.shopping

import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel

val imageUrl = "https://www.naver.com/"
val title = "올리브"
val price = 1500

fun product(id: Long) = Product(id, imageUrl, title, price)

fun cartItems(size: Int): List<CartItem> {
    return List(size) { CartItem(it.toLong(), it.toLong(), Quantity()) }
}

fun products(size: Int): List<Product> {
    return List(size) { product(it.toLong()) }
}

fun convertProductUiModel(cartItems: List<CartItem>, products: List<Product>): List<ProductUiModel> {
    return products.map { product ->
        val cartItem = cartItems.firstOrNull { it.productId == product.id }
        if (cartItem == null) {
            ProductUiModel.from(product)
        } else {
            ProductUiModel.from(product, cartItem.quantity)
        }
    }
}
