package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository

class FakeCartRepositoryImpl : CartRepository {
    val cartItems = mutableListOf<CartItem>()

    override fun insert(productWithQuantity: ProductWithQuantity) {
        cartItems.add(
            CartItem(
                id = productWithQuantity.product.id,
                productId = productWithQuantity.product.id,
                productName = productWithQuantity.product.name,
                price = productWithQuantity.product.price,
                imgUrl = productWithQuantity.product.imageUrl,
                quantity = productWithQuantity.quantity,
            ),
        )
    }

    override fun getQuantityByProductId(productId: Long): Int? {
        return cartItems.find { it.productId == productId }?.quantity
    }

    override fun plusQuantity(
        productId: Long,
        quantity: Int,
    ) {
        return
    }

    override fun minusQuantity(
        productId: Long,
        quantity: Int,
    ) {
        return
    }

    override fun size(): Int {
        return cartItems.size
    }

    override fun sumQuantity(): Int {
        return cartItems.sumOf { it.quantity }
    }

    override fun findWithProductId(productId: Long): CartItem {
        return cartItems.find { it.productId == productId }!!
    }

    override fun findCartItemsByPage(
        page: Int,
        pageSize: Int,
    ): ShoppingCart {
        return ShoppingCart(cartItems)
    }

    override fun deleteByProductId(productId: Long) {
        cartItems.removeIf { it.productId == productId }
    }
}
