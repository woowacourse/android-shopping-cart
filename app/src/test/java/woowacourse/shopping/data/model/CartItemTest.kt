package woowacourse.shopping.data.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartItemTest {
    @Test
    fun `장바구니로 담은 상품의 수량이 최소 개수보다 적으면 예외를 발생시킨다`() {
        // given
        // when
        val actualMessage = assertThrows<IllegalArgumentException> { CartItem(1, 0, 1) }.message

        // then
        assertThat(actualMessage).isEqualTo("카트 아이템 수량은 최소 1개 이상이어야 한다.")
    }

    @Test
    fun `장바구니로 담은 상품의 아이디가 유효하지 않는 아이디라면 예외를 발생시킨다`() {
        // given
        // when
        val actualMessage = assertThrows<IllegalArgumentException> { CartItem(0, 1, 1) }.message

        // then
        assertThat(actualMessage).isEqualTo("카트 아이템 아이디는 최소 1이상이어야 한다.")
    }
}
