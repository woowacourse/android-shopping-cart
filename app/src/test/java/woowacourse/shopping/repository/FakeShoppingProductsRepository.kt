package woowacourse.shopping.repository

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class FakeShoppingProductsRepository(
    private val cartItems: List<ProductData> = listOf(),
    private val pagingStrategy: PagingStrategy<ProductData> = NumberPagingStrategy(countPerLoad = 20),
) : ShoppingProductsRepository {
    override fun loadAllProducts(page: Int): List<ProductData> = pagingStrategy.loadPagedData(page, cartItems)

    override fun loadProduct(findId: Int): ProductData =
        cartItems.find { it.id == findId } ?: throw NoSuchElementException("there is no product with id: $findId")

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, cartItems)
}
