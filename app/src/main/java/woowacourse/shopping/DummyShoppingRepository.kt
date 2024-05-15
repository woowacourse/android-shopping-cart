package woowacourse.shopping

import woowacourse.shopping.domain.ImageUrl
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart
import woowacourse.shopping.domain.User

object DummyShoppingRepository : ShoppingRepository {
    private var users = listOf(
        User(
            id = 0L,
            shoppingCart = ShoppingCart(emptyList())
        )
    )

    private val products =
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

    override fun productById(id: Long): Product = products.firstOrNull { it.id == id } ?: error(
        "$id 에 해당하는 상품이 없습니다."
    )

    override fun userId(): Long = users.first().id

    override fun shoppingCart(userId: Long): ShoppingCart =
        users.firstOrNull { it.id == userId }?.shoppingCart ?: error("$userId 에 해당하는 유저가 없습니다.")

    override fun updateShoppingCart(shoppingCart: ShoppingCart) {
       this.users = users.map {
           if (it.id == userId()) it.copy(shoppingCart = shoppingCart) else it
       }
    }
}
