package woowacourse.shopping.data.product

import com.example.domain.Product
import com.example.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    override fun getAll(): List<Product> {
        return productDao.getAll()
    }
}
