@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class RecentProductTest {
    @Test
    fun `조회 시각이 0 이상이면 최근 본 상품을 생성할 수 있다`() {
        val productId = ProductId(UUID.randomUUID())

        val actual = RecentProduct(productId = productId, viewedAtMillis = 0)

        assertEquals(productId, actual.productId)
        assertEquals(0, actual.viewedAtMillis)
    }

    @Test
    fun `조회 시각이 음수이면 최근 본 상품을 생성할 수 없다`() {
        val productId = ProductId(UUID.randomUUID())

        assertThrows<IllegalArgumentException> {
            RecentProduct(productId = productId, viewedAtMillis = -1)
        }
    }
}
