package woowacourse.shopping.fixture

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

object ShoppingFixture {
    fun getProduct(
        id: String = "1",
        name: String = "bolt",
        price: Int = 10_000,
        url: String = "",
    ): Product = Product(id = id, name = name, price = price, imageUrl = url)

    fun getCartItem(
        product: Product = getProduct(),
        quantity: Int = 2,
    ): CartItem = CartItem(product = product, quantity = quantity)
}
