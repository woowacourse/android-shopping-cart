package woowacourse.shopping.data

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl : CartRepository {
    override fun getCartItems(): List<CartItem> = CartDataSource.items

    override fun addItem(
        product: Product,
        amount: Int,
    ) {
        CartDataSource.add(CartItem(product, amount))
    }

    override fun deleteItem(id: String) {
        CartDataSource.deleteItem(id)
    }
}
