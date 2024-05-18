package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CartPageManagerTest {
    lateinit var cartPageManager: CartPageManager

    @BeforeEach
    fun setUp() {
        cartPageManager = CartPageManager(5)
    }

    @Test
    fun `페이지 번호의 처음 상태는 1이어야 한다`() {
        assertThat(cartPageManager.pageNum).isEqualTo(1)
    }

    @Test
    fun `페이지를 플러스하면 페이지 번호가 증가해야 한다`() {
        // when
        cartPageManager.plusPageNum()
        // then
        assertThat(cartPageManager.pageNum).isEqualTo(2)
    }

    @Test
    fun `페이지를 마이너스하면 페이지 번호가 감소해야 한다`() {
        // given
        cartPageManager.plusPageNum()
        // when
        cartPageManager.minusPageNum()
        // then
        assertThat(cartPageManager.pageNum).isEqualTo(1)
    }

    @Test
    fun `페이지 번호가 1이면 마이너스를 해도 페이지 번호가 감소하지 않아야 한다`() {
        // when
        repeat(10) {
            cartPageManager.minusPageNum()
        }
        // then
        assertThat(cartPageManager.pageNum).isEqualTo(1)
    }

    @Test
    fun `첫 번째 페이지에서는 뒤로 갈 수 없다`() {
        // when
        val actual = cartPageManager.canMovePreviousPage()
        // then
        assertThat(actual).isFalse()
    }

    @Test
    fun `두 번째 이상의 페이지에서는 뒤로 갈 수 있다`() {
        // given
        cartPageManager.plusPageNum()
        // when
        val actual = cartPageManager.canMovePreviousPage()
        // then
        assertThat(actual).isTrue()
    }
}
