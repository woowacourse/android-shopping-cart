package woowacourse.shopping.data.repository.product.source.local

import com.example.domain.Product

interface ProductLocalDataSource {
    fun getAll(): List<Product>
    fun deleteColumn(product: Product)
}
