@file:Suppress("ktlint")

package woowacourse.shopping.view.page

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.shopping.data.DummyInventoryRepository

class DummyInventoryRepositoryTest {
    @Test
    fun 페이지의_크기만큼_항목이_들어있다() {
        // given
        val repository = DummyInventoryRepository()

        // when
        val page = repository.getPage(5, 0)

        // then
        val actual = page.items
        val expected = repository.getAll().subList(0, 5)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun 첫_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        // given
        val repository = DummyInventoryRepository()

        // when
        val page = repository.getPage(5, 0)

        // then
        assertThat(page.hasPrevious).isFalse()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 첫_페이지의_인덱스는_0이다() {
        // given
        val repository = DummyInventoryRepository()

        // when
        val page = repository.getPage(5, 0)

        // then
        val actual = page.pageIndex
        val expected = 0
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun 중간에_있는_페이지는_이전_페이지와_다음_페이지가_있다() {
        // given
        val repository = DummyInventoryRepository()

        // when
        val page = repository.getPage(5, 1)

        // then
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isTrue()
    }

    @Test
    fun 마지막_페이지는_이전_페이지가_없고_다음_페이지가_있다() {
        // given
        val repository = DummyInventoryRepository()

        // when
        val page = repository.getPage(5, 5)

        // then
        assertThat(page.hasPrevious).isTrue()
        assertThat(page.hasNext).isFalse()
    }
}
