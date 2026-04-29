package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.CartDataSource
import woowacourse.shopping.data.source.CartDataSourceImpl
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource = CartDataSourceImpl,
) : CartRepository {
    override fun getCartItems(): List<CartItem> = cartDataSource.items

    override fun addItem(
        product: Product,
        amount: Int,
    ) {
        cartDataSource.add(CartItem(product, amount))
    }

    override fun deleteItem(id: String) {
        cartDataSource.deleteItem(id)
    }
}
