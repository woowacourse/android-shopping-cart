package woowacourse.shopping.data

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepository : ProductRepository {

    override fun getAll(): List<Product> {
        return productsDatasource
    }

    override fun getNextProducts(lastProductId: Long): List<Product> {
        val toIndex = (lastProductId + 20).toInt()
        return if (lastProductId + 20 > productsDatasource.size) {
            productsDatasource.subList((lastProductId + 1).toInt(), productsDatasource.size)
        } else {
            productsDatasource.subList((lastProductId + 1).toInt(), toIndex)
        }
    }
}
