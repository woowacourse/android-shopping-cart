package woowacourse.shopping.presentaion.ui.cart

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentaion.ui.testCartItem0

class FakeCartRepositoryImpl : CartRepository {
    val cartItems = mutableListOf<CartItem>()

    override fun insert(
        product: Product,
        quantity: Int,
    ) {
        cartItems.add(
            CartItem(
                id = product.id,
                productId = product.id,
                productName = product.name,
                price = product.price,
                imgUrl = product.imageUrl,
                quantity = quantity,
            ),
        )
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
    }

    override fun size(): Int {
        return cartItems.size
    }

    override fun findOrNullWithProductId(productId: Long): CartItem? {
        return null
    }

    override fun find(cartItemId: Long): CartItem {
        return testCartItem0
    }

    override fun findAll(): ShoppingCart {
        return ShoppingCart(cartItems)
    }

    override fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): ShoppingCart {
        return ShoppingCart(cartItems)
    }

    override fun delete(cartItemId: Long) {
        cartItems.removeIf { it.id == cartItemId }
    }

    override fun deleteAll() {
        cartItems.clear()
    }
}
