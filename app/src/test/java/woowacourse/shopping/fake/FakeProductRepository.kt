package woowacourse.shopping.fake

import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.repository.ProductRepository
import kotlin.math.min

class FakeProductRepository(
    itemSize: Int = 1,
) : ProductRepository {
    private val products =
        List(itemSize) { index ->
            Product(
                id = (index + 1).toString(),
                title = ProductTitle("호날두"),
                price = Price(10_000),
                imageUrl = "",
            )
        }

    override suspend fun totalSize(): Int = products.size

    override suspend fun getProduct(productId: String): Product? = products.find { it.id == productId }

    override suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<Product> = products.subList(offset, min(offset + size, products.size))
}
