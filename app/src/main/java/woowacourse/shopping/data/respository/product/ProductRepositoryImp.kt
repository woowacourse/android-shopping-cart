package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.model.ProductEntity

class ProductRepositoryImp : ProductRepository {
    override fun getData(): List<ProductEntity> {
        return ProductsDao.getData()
    }

    override fun getDataById(id: Long): ProductEntity {
        return ProductsDao.getDataById(id) ?: ProductsDao.getErrorData()
    }
}
