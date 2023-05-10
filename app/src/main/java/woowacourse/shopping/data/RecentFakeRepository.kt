package woowacourse.shopping.data

import com.example.domain.model.RecentRepository
import com.example.domain.model.Product

object RecentFakeRepository : RecentRepository {

    private val products = MutableList(20) {
        Product(
            it,
            "[사미헌] 갈비탕$it",
            12000,
            "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1648206780555l0.jpeg"
        )
    }

    override fun findById(id: Int): Product {
        return products.find {
            it.id == id
        } ?: throw IllegalArgumentException("해당하는 아이템이 없습니다.")
    }

    override fun delete(id: Int): Boolean {
        return products.removeIf {
            it.id == id
        }
    }

    override fun getRecent(maxSize: Int): List<Product> {
        return products.toList().subList(0, maxSize)
    }
}
