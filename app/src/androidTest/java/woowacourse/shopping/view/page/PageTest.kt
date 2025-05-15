@file:Suppress("ktlint")

package woowacourse.shopping.view.page

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.shopping.products

class PageTest {
    @Test
    fun 페이지의_크기만큼_항목이_들어있다() {
        val page =
            Page.from(
                products,
                0,
                5,
            )
        assertThat(page.items).isEqualTo(products.subList(0, 5))
        assertThat(page.items.size).isEqualTo(5)
    }

    @Test
    fun 첫_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        val page =
            Page.from(
                products,
                0,
                5,
            )
        assertThat(page.hasPrevious).isFalse()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 첫_페이지의_인덱스는_0이다() {
        val page =
            Page.from(
                products,
                0,
                5,
            )
        assertThat(page.currentPage).isEqualTo(0)
    }

    @Test
    fun 중간에_있는_페이지는_이전_페이지와_다음_페이지가_있다() {
        val page =
            Page.from(
                products,
                1,
                5,
            )
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 마지막_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        val page =
            Page.from(
                products,
                5,
                5,
            )
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isFalse()
    }

    @Test
    fun 두_페이지를_더하면_각_페이지의_항목이_모두_들어있다() {
        val pageOne =
            Page.from(
                products,
                0,
                5,
            )
        val pageTwo =
            Page.from(
                products,
                1,
                5,
            )
        val actualPage = pageOne + pageTwo

        val expectedPage =
            Page.from(
                products,
                0,
                10,
            )
        assertThat(actualPage).isEqualTo(expectedPage.copy(currentPage = 1))
    }
}
