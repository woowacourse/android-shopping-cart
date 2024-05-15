package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Product
import kotlin.math.min

class DummyProductRepository : ProductRepository {
    private val products =
        List(51) { id ->
            Product(
                id = id.toLong(),
                imgUrl = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg",
                name = "$id",
                price = 10000,
            )
        }

    override fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            val startIndex = pageOffset * pageSize
            val endIndex = min(startIndex + pageSize, products.size)
            products.subList(startIndex, endIndex)
        }

    override fun loadById(id: Long): Result<Product> =
        runCatching {
            products.first { it.id == id }
        }
}
