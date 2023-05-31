package woowacourse.shopping.data.datasource.recentproductdatasource

import com.example.domain.Product
import com.example.domain.RecentProducts

interface RecentProductSource {
    fun getRecentProducts(): RecentProducts
    fun getLastProduct(): Product?
    fun addColumn(product: Product)
}
