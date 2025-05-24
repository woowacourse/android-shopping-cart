@file:Suppress("ktlint")

package woowacourse.shopping.view.page

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.shopping.data.page.Page
import woowacourse.shopping.fixture.TestProducts

class PageTest {
    @Test
    fun 페이지의_크기만큼_항목이_들어있다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0,5),
                TestProducts.productUiModels.size,
                0,
                5,
            )
        assertThat(page.items).isEqualTo(TestProducts.productUiModels.subList(0, 5))
        assertThat(page.items.size).isEqualTo(5)
    }

    @Test
    fun 첫_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0,5),
                TestProducts.productUiModels.size,
                0,
                5,
            )
        assertThat(page.hasPrevious).isFalse()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 첫_페이지의_인덱스는_0이다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0,5),
                TestProducts.productUiModels.size,
                0,
                5,
            )
        assertThat(page.currentPage).isEqualTo(0)
    }

    @Test
    fun 중간에_있는_페이지는_이전_페이지와_다음_페이지가_있다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0,5),
                TestProducts.productUiModels.size,
                1,
                5,
            )
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 마지막_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        val page =
            Page(
                TestProducts.productUiModels.subList(0,5),
                TestProducts.productUiModels.size,
                5,
                5,
            )
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isFalse()
    }
}
