package woowacourse.shopping

import woowacourse.shopping.domain.ImageUrl
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import java.time.LocalDateTime
import kotlin.math.min

object DummyProductRepository : ProductRepository {
    private val products: List<Product> = moreDummies()

    private const val RECENT_SIZE = 10

    private var recentProducts = RecentProducts(emptyList())

    private fun moreDummies(): List<Product> {
        val products = mutableListOf<Product>()

        for (i in 0 until 60) {
            when (i % 6) {
                0 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-납작(260ml)",
                            Price(61800),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/fed0b624-737a-4e4a-be3e-2e8a8cd953fc",
                            ),
                        ),
                    )

                1 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-원형(200ml)",
                            Price(88800),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/015018f9-f668-4e71-94c8-cb214f034321",
                            ),
                        ),
                    )

                2 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-정사각(370ml)",
                            Price(41000),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0d14a4d3-312d-4688-8699-f2c269c70ce5",
                            ),
                        ),
                    )

                3 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-밀크티(250ml)",
                            Price(12000),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/11533147-d5b7-4d41-9668-03d8b5dcbb7b",
                            ),
                        ),
                    )

                4 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-단지(100ml)",
                            Price(10000),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0277eaa6-f0a4-4be6-8b9d-7b732cd411f4",
                            ),
                        ),
                    )

                5 ->
                    products.add(
                        Product(
                            i.toLong(),
                            "PET보틀-단지(150ml)",
                            Price(12000),
                            ImageUrl(
                                "https://github.com/woowacourse/android-movie-theater/assets/92314556/0e4959a4-08a7-40e4-a876-a832495233be",
                            ),
                        ),
                    )
            }
        }

        return products.toList()
    }

    override fun products(): List<Product> = products

    override fun products(
        startPosition: Int,
        offset: Int,
    ): List<Product> {
        val additional = min(startPosition + offset, products.size)
        return products.subList(startPosition, additional)
    }

    override fun productById(id: Long): Product =
        products.firstOrNull { it.id == id } ?: error(
            "$id 에 해당하는 상품이 없습니다.",
        )

    override fun productsTotalSize(): Int = products.size

    override fun sortedRecentProduct() = recentProducts.sortedRecentProduct()

    override fun addRecentProduct(
        productId: Long,
        localDateTime: LocalDateTime,
    ) {
        val product = productById(productId)
        recentProducts = recentProducts.add(RecentProduct(product, localDateTime))
    }
}
