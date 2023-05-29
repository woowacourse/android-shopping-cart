package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasketProductTest {
    @Test
    fun `각 13000원짜리 상품이 5개 있다면 총합 계산이 65000이 된다`() {
        // given
        val basketProduct =
            BasketProduct(1, Count(5), Product(1, "그것은 더미 입니다만", Price(13000), "url"))

        // when
        val actual = basketProduct.getTotalPrice()
        val expected = Price(65000)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
