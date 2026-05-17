package woowacourse.shopping.fixture

import woowacourse.shopping.data.source.remote.model.ProductResponse
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

object ShoppingFixture {
    fun getProduct(
        id: String = "1",
        name: String = "bolt",
        price: Price = Price(10_000),
        url: String = "",
    ): Product = Product(id = id, name = name, price = price, imageUrl = url)

    fun getProductResponse(
        id: String = "1",
        name: String = "bolt",
        price: Long = 10_000,
        url: String = "",
    ): ProductResponse = ProductResponse(id = id, name = name, price = price, imageUrl = url)

    fun getCartItem(
        product: Product = getProduct(),
        quantity: Int = 2,
    ): CartItem = CartItem(productId = product.id, quantity = quantity)
}
