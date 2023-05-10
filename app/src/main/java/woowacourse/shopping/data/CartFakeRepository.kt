package woowacourse.shopping.data

import com.example.domain.model.CartRepository
import com.example.domain.model.Product
import java.lang.Integer.min

object CartFakeRepository : CartRepository {

    private val products = MutableList(6) {
        Product(
            it,
            "[사미헌] 갈비탕$it",
            12000,
            "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg" // ktlint-disable max-line-length
        )
    }

    override fun getAll(): List<Product> {
        return products.toList()
    }

    override fun getSubList(offset: Int, size: Int): List<Product> {
        return products.subList(offset, min(offset + size, products.size))
    }

    override fun add(item: Product) {
        products.add(item)
    }

    override fun remove(id: Int) {
        products.removeIf {
            it.id == id
        }
    }
}
