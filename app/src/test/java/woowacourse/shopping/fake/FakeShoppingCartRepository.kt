package woowacourse.shopping.fake

import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository

class FakeShoppingCartRepository(
    itemSize: Int = 1,
    initialQuantity: Int = 0,
) : ShoppingCartRepository {
    private val product = Product("1", ProductTitle("호날두"), Price(10_000), "")
    private val quantities =
        MutableList(itemSize) { index ->
            (index + 1).toString() to initialQuantity
        }.toMap()
            .toMutableMap()

    override suspend fun addItemToCart(
        productId: String,
        amount: Int,
    ) {
        quantities[productId] = (quantities[productId] ?: 0) + amount
    }

    override suspend fun decreaseItemQuantity(
        productId: String,
        amount: Int,
    ) {
        val updatedQuantity = (quantities[productId] ?: 0) - amount
        if (updatedQuantity > 0) {
            quantities[productId] = updatedQuantity
        } else {
            quantities.remove(productId)
        }
    }

    override suspend fun removeItemFromCart(productId: String) {
        quantities.remove(productId)
    }

    override suspend fun getCartItem(productId: String): ShoppingCartItem? {
        val quantity = quantities[productId] ?: return null
        return ShoppingCartItem(Quantity(quantity), product.copy(id = productId))
    }

    override suspend fun getCartItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem> {
        val shoppingCartItems =
            quantities.map { (productId, quantity) ->
                ShoppingCartItem(Quantity(quantity), product.copy(id = productId))
            }
        val offset = offset.coerceIn(0, shoppingCartItems.size)
        return shoppingCartItems.subList(offset, minOf(offset + size, shoppingCartItems.size))
    }

    override suspend fun getTotalQuantity(): Int = quantities.values.sum()

    override suspend fun getTotalSize(): Int = quantities.size
}
