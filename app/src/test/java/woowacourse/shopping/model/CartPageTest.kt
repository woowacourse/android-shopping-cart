package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CartPageTest {
    private lateinit var cartPage: CartPage

    @BeforeEach
    fun setUp() {
        cartPage = CartPage()
    }

    @Test
    fun `페이지 번호의 처음 상태는 1이어야 한다`() {
        assertThat(cartPage.number).isEqualTo(1)
    }

    @Test
    fun `페이지를 플러스하면 페이지 번호가 증가해야 한다`() {
        // when
        cartPage = cartPage.plus()
        // then
        assertThat(cartPage.number).isEqualTo(2)
    }

    @Test
    fun `페이지가 1이 아닐 때 마이너스하면 페이지 번호가 감소해야 한다`() {
        // given
        cartPage = CartPage(2)
        // when
        cartPage = cartPage.minus()
        // then
        assertThat(cartPage.number).isEqualTo(1)
    }

    @Test
    fun `페이지 번호가 1이면 마이너스를 해도 페이지 번호가 감소하지 않아야 한다`() {
        // when
        repeat(10) {
            cartPage = cartPage.minus()
        }
        // then
        assertThat(cartPage.number).isEqualTo(1)
    }

    @Test
    fun `첫 번째 페이지에서는 뒤로 갈 수 없다`() {
        // when
        val actual = cartPage.isNotFirst()
        // then
        assertThat(actual).isFalse()
    }

    @Test
    fun `두 번째 이상의 페이지에서는 뒤로 갈 수 있다`() {
        // given
        cartPage = CartPage(2)
        // when
        val actual = cartPage.isNotFirst()
        // then
        assertThat(actual).isTrue()
    }
}
