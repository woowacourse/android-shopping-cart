package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.mapper.toProduct

class RoomProductsRepositoryImpl(
    private val productDao: ProductDao,
) : ProductsRepository {
    override suspend fun findAll(pageRequest: PageRequest): Page<Product> {
        val offset = pageRequest.requestPage * pageRequest.pageSize
        val limit = pageRequest.pageSize
        val items =
            productDao.findAll(offset, limit).map {
                it.toProduct()
            }
        return pageRequest.toPage(items, totalSize())
    }

    override suspend fun totalSize(): Int {
        return productDao.count()
    }
}
