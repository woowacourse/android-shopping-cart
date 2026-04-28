package woowacourse.shopping.repository.inmemory

import woowacourse.shopping.R
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import woowacourse.shopping.repository.ProductRepository

object InMemoryProductRepository : ProductRepository {
    val BOTTLE_SQUARE_500ML = Product(
        name = "PET보틀 -정사각(500ml)",
        price = Money(41_000),
        imageResource = R.drawable.bottle_square_500
    )

    val BOTTLE_SQUARE_370ML = Product(
        name = "PET보틀 -정사각(370ml)",
        price = Money(41_000),
        imageResource = R.drawable.bottle_square_370
    )

    val BOTTLE_FLAT_260ML = Product(
        name = "PET보틀-납작(260ml)",
        price = Money(61_800),
        imageResource = R.drawable.bottle_flat_260
    )

    val SWEET_CORN = Product(
        name = "[든든] 동원 스위트콘",
        price = Money(99_800),
        imageResource = R.drawable.sweet_corn
    )

    val BOTTLE_JAR_400ML = Product(
        name = "PET보틀-단지(400ml)",
        price = Money(12_000),
        imageResource = R.drawable.bottle_jar_400
    )


    val BOTTLE_JAR_200ML = Product(
        name = "PET보틀-단지(200ml)",
        price = Money(10_000),
        imageResource = R.drawable.bottle_jar_200
    )

    private val products = Products(
        listOf(
            BOTTLE_SQUARE_500ML,
            BOTTLE_SQUARE_370ML,
            BOTTLE_FLAT_260ML,
            BOTTLE_JAR_400ML,
            BOTTLE_JAR_200ML,
            SWEET_CORN
        )
    )

    override fun getAllProduct() = products
}
