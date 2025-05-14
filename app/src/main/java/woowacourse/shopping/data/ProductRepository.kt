package woowacourse.shopping.data

import woowacourse.shopping.model.Product
import kotlin.math.min

class ProductRepository {
    private val products: List<Product> =
        List(43) {
            Product(
                name = "$it 우테코 과자",
                price = 10_000,
                imageUrl = "https://avatars.githubusercontent.com/u/112997521?v=4"
            )
        }

    private var offset = 0

    fun getNextPage(): List<Product> {
        val from = offset
        offset = min(offset + PAGE_SIZE, products.size)
        return products.subList(from, offset)
    }

    fun initProducts() {
        offset = 0
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
