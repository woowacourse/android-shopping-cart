package woowacourse.shopping.repository

import java.util.UUID
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId

object CartRepositoryFixture {
    val shrimpCracker =
        Product(
            id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
            name = "새우깡",
            price = Money(3_100),
            imageUrl = "",
        )

    val sourCandy =
        Product(
            id = ProductId(UUID.fromString("00000000-0000-0000-0000-000000000002")),
            name = "아이셔",
            price = Money(1_300),
            imageUrl = "",
        )

    val products = listOf(shrimpCracker, sourCandy)
}
