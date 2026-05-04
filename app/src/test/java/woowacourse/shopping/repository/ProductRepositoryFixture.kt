package woowacourse.shopping.repository

import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import java.util.UUID

object ProductRepositoryFixture {
    val products: List<Product> =
        List(24) { index ->
            Product(
                id = ProductId(UUID.fromString("00000000-0000-0000-0000-%012d".format(index + 1))),
                name = "상품${index + 1}",
                price = Money(10_000 + index * 1_000),
                imageUrl = "https://example.com/product-${index + 1}.png",
            )
        }
}
