package woowacourse.shopping

import woowacourse.shopping.domain.ImageUrl
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

object DummyShoppingRepository : ShoppingRepository {
    val products =
        listOf(
            Product(
                0,
                "PET보틀-납작(260ml)",
                Price(61800),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/fed0b624-737a-4e4a-be3e-2e8a8cd953fc"),
            ),
            Product(
                1,
                "PET보틀-원형(200ml)",
                Price(88800),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/015018f9-f668-4e71-94c8-cb214f034321"),
            ),
            Product(
                2,
                "PET보틀-정사각(370ml)",
                Price(41000),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/0d14a4d3-312d-4688-8699-f2c269c70ce5"),
            ),
            Product(
                3,
                "PET보틀-밀크티(250ml)",
                Price(12000),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/11533147-d5b7-4d41-9668-03d8b5dcbb7b"),
            ),
            Product(
                4,
                "PET보틀-단지(100ml)",
                Price(10000),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/0277eaa6-f0a4-4be6-8b9d-7b732cd411f4"),
            ),
            Product(
                5,
                "PET보틀-단지(150ml)",
                Price(12000),
                ImageUrl("https://github.com/woowacourse/android-movie-theater/assets/92314556/0e4959a4-08a7-40e4-a876-a832495233be"),
            ),
        )

    override fun products(): List<Product> = products
}
