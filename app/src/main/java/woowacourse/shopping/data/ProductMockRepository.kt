package woowacourse.shopping.data

import com.example.domain.datasource.productsDatasource
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class ProductMockRepository : ProductRepository {

    override fun getFirstProducts(): List<Product> {
        return if (LOAD_SIZE > productsDatasource.size) {
            productsDatasource
        } else {
            productsDatasource.subList(0, LOAD_SIZE)
        }
    }

    override fun getNextProducts(lastProductId: Long): List<Product> {
        val toIndex = (lastProductId + LOAD_SIZE).toInt()
        return if (lastProductId + LOAD_SIZE > productsDatasource.size) {
            productsDatasource.subList((lastProductId + 1).toInt(), productsDatasource.size)
        } else {
            productsDatasource.subList((lastProductId + 1).toInt(), toIndex)
        }
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
