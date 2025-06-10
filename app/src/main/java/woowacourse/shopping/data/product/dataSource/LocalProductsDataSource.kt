package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.entity.ProductEntity

object LocalProductsDataSource : ProductsDataSource {
    private lateinit var dao: ProductDao

    fun init(productDao: ProductDao) {
        dao = productDao
    }

    override fun load(): List<ProductEntity> = dao.loadAll()

    override fun getById(id: Long): ProductEntity? = dao.load(id)
}
