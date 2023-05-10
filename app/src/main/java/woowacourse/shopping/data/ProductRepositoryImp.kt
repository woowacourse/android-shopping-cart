package woowacourse.shopping.data

import woowacourse.shopping.data.model.ProductEntity

class ProductRepositoryImp : ProductRepository {
    override fun getData(): List<ProductEntity> {
        return ProductsDao.getData()
    }
}
