package woowacourse.shopping.cart

import org.junit.Test
import woowacourse.shopping.ui.cart.Page

class PageTest {
    @Test(expected = IllegalArgumentException::class)
    fun 페이지는_0보다_작을_수_없다() {
        // given

        // when
        Page(-1)

        // then
    }
}
