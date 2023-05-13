package woowacourse.shopping.data

import com.example.domain.model.Product
import com.example.domain.model.ProductRepository
import java.lang.Integer.min

object ProductFakeRepository : ProductRepository {

    const val KEY_PRODUCT_OFFSET = "bundle_key_offset"

    private val products = List(100) {
        Product(
            it,
            "[사미헌] 갈비탕$it",
            12000,
            "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg",
        )
    }

    var offset = 0
        private set

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun getNext(count: Int): List<Product> {
        val from = offset
        val to = min(offset + count, products.size)
        val subList = products.subList(from, to)
        offset = to
        return subList
    }

    override fun getUntil(offset: Int): List<Product> {
        val to = min(offset, products.size)
        val subList = products.subList(0, to)
        this.offset = to
        return subList
    }

    override fun findById(id: Int): Product {
        return getAll().find {
            it.id == id
        } ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
    }
}
