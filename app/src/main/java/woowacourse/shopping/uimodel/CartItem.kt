package woowacourse.shopping.uimodel

import woowacourse.shopping.domain.Product

data class CartItem(
    val cartItemId: Long,
    val product: Product,
) {
    val name: String
        get() = product.name

    val imageUrl: String
        get() = product.imageUrl

    val price: Int
        get() = product.price
}
