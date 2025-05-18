package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dao: ProductDao,
) : ProductRepository {
    fun fetchProducts(
        lastId: Int,
        count: Int,
    ): Products =
        Products(
            products = dao.getNextProducts(lastId, count).map { it.toDomain() },
            hasMore = fetchHasMoreProducts(lastId),
        )

    private fun fetchHasMoreProducts(lastId: Int): Boolean {
        val maxId = dao.getMaxId()
        return maxId > lastId
    }

    fun fetchProductDetail(id: Int): Product = dao.getProduct(id).toDomain()
}
