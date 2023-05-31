package woowacourse.shopping.data.datasource.productdatasource

import com.example.domain.Product

interface ProductDataSource {
    fun requestAllData(): List<Product>
}
