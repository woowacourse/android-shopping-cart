package woowacourse.shopping.cart

import org.junit.Test

class PageTest {
    @Test(expected = IllegalArgumentException::class)
    fun 페이지는_0보다_작을_수_없다() {
        // given

        // when
        Page(-1)

        // then
    }
}
