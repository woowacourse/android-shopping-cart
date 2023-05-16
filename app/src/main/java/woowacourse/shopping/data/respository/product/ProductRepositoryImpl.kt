package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.model.ProductEntity

class ProductRepositoryImpl : ProductRepository {
    override fun getData(startPosition: Int, count: Int): List<ProductEntity> {
        return ProductsDao.getData(startPosition, count)
    }

    override fun getDataById(id: Long): ProductEntity {
        return ProductsDao.getDataById(id) ?: ProductsDao.getErrorData()
    }
}
