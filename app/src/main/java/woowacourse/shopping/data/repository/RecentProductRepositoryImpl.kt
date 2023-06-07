package woowacourse.shopping.data.repository

import com.example.data.repository.RecentProductRepository
import com.example.domain.Product
import com.example.domain.RecentProducts
import woowacourse.shopping.data.datasource.recentproductdatasource.RecentProductSource

class RecentProductRepositoryImpl(
    private val recentProductDataSource: RecentProductSource,
) : RecentProductRepository {
    override fun getRecentProducts(): RecentProducts {
        return recentProductDataSource.getRecentProducts()
    }

    override fun getLastProduct(): Product? {
        return recentProductDataSource.getLastProduct()
    }

    override fun addColumn(product: Product) {
        recentProductDataSource.addColumn(product)
    }
}
