package woowacourse.shopping.repository

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class FakeShoppingProductsRepository(
    private val cartItems: List<Product> = listOf(),
    private val pagingStrategy: PagingStrategy<Product> = NumberPagingStrategy(countPerLoad = 20),
) : ShoppingProductsRepository {
    override fun loadAllProducts(page: Int): List<Product> = pagingStrategy.loadPagedData(page, cartItems)

    override fun loadProduct(id: Int): Product =
        cartItems.find { it.id == id } ?: throw NoSuchElementException("there is no product with id: $id")

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, cartItems)
}
