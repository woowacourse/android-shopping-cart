package woowacourse.shopping.ui.extension

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Price

class PriceExtensionTest {
    @Test
    fun `천의 자리가 넘어간다면 콤마가 들어간다`() {
        val price = Price(10_000_000)

        price.toFormattedPrice() shouldBe "10,000,000원"
    }

    @Test
    fun `천의 자리 미만이라면 콤마가 들어가지 않는다`() {
        val price = Price(100)

        price.toFormattedPrice() shouldBe "100원"
    }
}
