package woowacourse.shopping.view.shoppingCart

import woowacourse.shopping.data.shoppingCart.repository.ShoppingCartRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product

class FakeShoppingCartRepository : ShoppingCartRepository {
    private var cartItems: List<CartItem> = List(50) { CartItem(it.toLong(), "이름", 1_000, 5) }

    override fun load(onLoad: (Result<List<CartItem>>) -> Unit) {
        onLoad(runCatching(::cartItems))
    }

    override fun upsert(
        cartItem: CartItem,
        onAdd: (Result<Unit>) -> Unit,
    ) {
        onAdd(runCatching { cartItems += cartItem })
    }

    override fun remove(
        cartItem: CartItem,
        onRemove: (Result<Unit>) -> Unit,
    ) {
        onRemove(runCatching { cartItems -= cartItem })
    }

    override fun update(
        cartItems: List<CartItem>,
        onUpdate: (Result<Unit>) -> Unit,
    ) {
        onUpdate(runCatching { this.cartItems = cartItems })
    }

    override fun quantityOf(
        product: Product,
        onResult: (Result<Int>) -> Unit,
    ) {
        onResult(runCatching { cartItems.find { it.id == product.id }?.quantity ?: 0 })
    }
}
