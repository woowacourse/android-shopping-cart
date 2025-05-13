package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

class ProductStorage {
    private val products = mutableMapOf<Long, Product>()

    init {
        initialize()
    }

    fun initialize() {
        listOf(
            Product(
                id = 1L,
                name = "PET보틀-정사각(370ml)",
                poster = "pet_bottle_square_370ml",
                price = Price(10000),
            ),
            Product(
                id = 2L,
                name = "PET보틀-정사각(500ml)",
                poster = "pet_bottle_square_500ml",
                price = Price(10000),
            ),
            Product(
                id = 3L,
                name = "PET보틀-밀크티(4500ml)",
                poster = "pet_bottle_milktea",
                price = Price(12000),
            ),
            Product(
                id = 4L,
                name = "PET보틀-납작(270ml)",
                poster = "pet_bottle_flat",
                price = Price(12000),
            ),
            Product(
                id = 5L,
                name = "PET보틀-단지(270ml)",
                poster = "pet_bottle_jar",
                price = Price(10000),
            ),
            Product(
                id = 6L,
                name = "PET보틀-단지-레몬(270ml)",
                poster = "pet_bottle_jar_lemon",
                price = Price(10000),
            ),
            Product(
                id = 7L,
                name = "PET보틀-원형(500ml)",
                poster = "pet_bottle_round_500ml",
                price = Price(12000),
            ),
        ).forEach {
            products[it.id] = it
        }
    }

    operator fun get(id: Long): Product = products[id] ?: throw IllegalArgumentException()

    fun getAll() = products.values.toList()
}
