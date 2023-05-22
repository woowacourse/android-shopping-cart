package woowacourse.shopping.data.respository.product

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.data.respository.product.source.remote.ProductRemoteDataSource
import woowacourse.shopping.data.respository.product.source.remote.ProductRemoteDataSourceImpl

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource = ProductRemoteDataSourceImpl()
) : ProductRepository {
    override fun loadData(): List<ProductEntity> {
        return productRemoteDataSource.requestDatas()
    }

    override fun loadDataById(id: Long): ProductEntity {
        return productRemoteDataSource.requestData(id)
    }
}
