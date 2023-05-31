package woowacourse.shopping.data.repository

import com.example.data.repository.ProductRepository
import com.example.domain.Product
import woowacourse.shopping.data.datasource.productdatasource.ProductDataSource

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductDataSource,
) : ProductRepository {
    override fun requestAll(): List<Product> {
        return productRemoteDataSource.requestAllData()
    }
}
