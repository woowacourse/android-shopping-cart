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
        storage = ProductStorageImpl()
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
                1L,
                "마리오 그린올리브 300g",
                Price(3980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            ),
        )
    }
}
