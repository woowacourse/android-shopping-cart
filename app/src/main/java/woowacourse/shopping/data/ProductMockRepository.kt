package woowacourse.shopping.data

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository

object ProductMockRepository : ProductRepository {

    private val products = List(100) {
        Product(
            it,
            "[사미헌] 갈비탕${it}",
            12000,
            "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
        )
    }

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun findById(id: Int): Product {
        return getAll().find {
            it.id == id
        } ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
    }
}
