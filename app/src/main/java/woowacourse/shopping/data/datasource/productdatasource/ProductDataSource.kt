package woowacourse.shopping.data.datasource.productdatasource

import com.example.domain.Product

interface ProductDataSource {
    fun getAll(): List<Product>
    fun deleteColumn(product: Product)
}
