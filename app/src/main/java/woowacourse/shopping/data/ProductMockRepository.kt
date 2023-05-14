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

    override fun getNextProducts(startIndex: Int): List<Product> {
        val toIndex = (startIndex + LOAD_SIZE)
        return if (toIndex > productsDatasource.size) {
            productsDatasource.subList((startIndex), productsDatasource.size)
        } else {
            productsDatasource.subList((startIndex), toIndex)
        }
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}
