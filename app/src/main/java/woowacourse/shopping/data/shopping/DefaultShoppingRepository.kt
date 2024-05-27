package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ShoppingRepository

class DefaultShoppingRepository(
    private val shoppingDataSource: ShoppingDataSource = DummyShoppingDataSource,
) : ShoppingRepository {
    override fun products(
        currentPage: Int,
        pageSize: Int,
    ): List<Product> {
        return shoppingDataSource.products(currentPage, pageSize)
    }

    override fun productById(id: Long): Product? {
        return shoppingDataSource.productById(id)
    }

    override fun canLoadMoreProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        return shoppingDataSource.canLoadMoreProducts(currentPage, pageSize)
    }

    override fun updateCount(
        id: Long,
        count: Int,
    ) {
        shoppingDataSource.updateProductCount(id, count)
    }
}
