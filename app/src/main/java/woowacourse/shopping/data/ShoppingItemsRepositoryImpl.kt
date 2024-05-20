package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingItemsRepositoryImpl(private val dataSource: ShoppingItemsDataSource) : ShoppingItemsRepository {
    override fun findProductItem(id: Long): Product? {
        return dataSource.getProductItem(id)
    }

    override fun findProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        return dataSource.getProducts(page, pageSize)
    }
}
