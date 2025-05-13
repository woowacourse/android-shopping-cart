package woowacourse.shopping.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

class ProductStorageTest {
    private lateinit var storage: ProductStorage

    @BeforeEach
    fun setUp() {
        storage = ProductStorage()
    }

    @Test
    fun `상품의 id를 전달받으면 id에 해당하는 삼품을 전달한다`() {
        // when
        val id = 1L

        // given
        val result = storage[id]

        assertEquals(
            result,
            Product(
                id = 1L,
                name = "PET보틀-정사각(370ml)",
                poster = "pet_bottle_square_370ml",
                price = Price(10000),
            ),
        )
    }
}
