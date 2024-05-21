package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun `상품 개수의 초기값은 1이다`() {
        val quantity = Quantity()
        assertThat(quantity.count).isEqualTo(1)
    }

    @Test
    fun `상품 개수가 3개일 때 증가시키면 4개가 된다`() {
        var quantity = Quantity(3)
        quantity++
        assertThat(quantity.count).isEqualTo(4)
    }

    @Test
    fun `상품 개수가 99개일 때 증가시켜도 변화가 없다`() {
        var quantity = Quantity(99)
        quantity++
        assertThat(quantity.count).isEqualTo(99)
    }

    @Test
    fun `상품 개수가 3개일 때 감소시키면 2개가 된다`() {
        var quantity = Quantity(3)
        quantity--
        assertThat(quantity.count).isEqualTo(2)
    }

    @Test
    fun `상품 개수가 1개일 때 감소시켜도 변화가 없다`() {
        var quantity = Quantity(1)
        quantity--
        assertThat(quantity.count).isEqualTo(1)
    }

    @Test
    fun `상품 개수가 1개면 상품 개수의 최소값이다`() {
        val quantity = Quantity(1)
        val actual = quantity.isMin()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품 개수가 5개면 상품 개수의 최소값이 아니다`() {
        val quantity = Quantity(5)
        val actual = quantity.isMin()
        assertThat(actual).isFalse
    }
}
