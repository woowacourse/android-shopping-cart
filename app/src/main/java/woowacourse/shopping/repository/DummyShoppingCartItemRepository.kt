package woowacourse.shopping.repository

import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.db.Product

class DummyShoppingCartItemRepository(private val pagingStrategy: PagingStrategy<Product>) :
    ShoppingCartItemRepository {
    override fun loadPagedData(): List<Product> = pagingStrategy.loadPagedData(0, cartItems)

    companion object {
        private val cartItems = mutableListOf<Product>()
    }
}
