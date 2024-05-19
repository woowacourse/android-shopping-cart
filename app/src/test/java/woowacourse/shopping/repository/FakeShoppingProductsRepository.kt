package woowacourse.shopping.repository

import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.TwentyItemsPagingStrategy
import woowacourse.shopping.data.Product

class FakeShoppingProductsRepository(
    private val cartItems: List<Product> = listOf(),
    private val pagingStrategy: PagingStrategy<Product> = TwentyItemsPagingStrategy(),
) : ShoppingProductsRepository {
    override fun loadPagedItems(page: Int): List<Product> = pagingStrategy.loadPagedData(page, cartItems)

    override fun findById(findId: Int): Product =
        cartItems.find { it.id == findId } ?: throw NoSuchElementException("there is no product with id: $findId")

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, cartItems)
}
