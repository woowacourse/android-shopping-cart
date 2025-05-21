package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.FakeCartStorage

class PageTest {
    private lateinit var page: Page
    private lateinit var fakeCartStorage: FakeCartStorage

    @BeforeEach
    fun setUp() {
        page = Page(initialPage = 1, pageSize = 5)
        fakeCartStorage =
            FakeCartStorage(
                products =
                    mutableListOf(
                        Product(1L, "맥북", Price(1000), ""),
                        Product(2L, "아이폰", Price(2000), ""),
                        Product(3L, "에어팟", Price(3000), ""),
                        Product(4L, "매직키보드", Price(4000), ""),
                        Product(5L, "에어팟맥스", Price(5000), ""),
                        Product(6L, "에어팟깁스", Price(6000), ""),
                    ),
            )
    }

    @Test
    fun `다음 페이지로 이동하면 페이지 번호가 증가한다`() {
        page.moveToNextPage()
        assertThat(page.getPageNumber()).isEqualTo(2)
    }

    @Test
    fun `이전 페이지는 초기 페이지보다 작아질 수 없다`() {
        page.moveToPreviousPage()
        assertThat(page.getPageNumber()).isEqualTo(1)
    }

    @Test
    fun `2페이지 이상일 때 이전 페이지로 이동하면 페이지 번호가 감소한다`() {
        page.moveToNextPage()
        page.moveToPreviousPage()
        assertThat(page.getPageNumber()).isEqualTo(1)
    }

    @Test
    fun `현재 페이지에 항목이 없으면 이전 페이지로 이동하고 true를 반환한다`() {
        page.moveToNextPage()
        val result = page.resetToLastPageIfEmpty(0)
        assertThat(result).isTrue()
        assertThat(page.getPageNumber()).isEqualTo(1)
    }

    @Test
    fun `현재 페이지에 항목이 있으면 이전 페이지로 이동하지 않고 false를 반환한다`() {
        val result = page.resetToLastPageIfEmpty(1)
        assertThat(result).isFalse()
        assertThat(page.getPageNumber()).isEqualTo(1)
    }

    @Test
    fun `다음 페이지가 존재하면 true를 반환한다`() {
        val result = page.hasNextPage(fakeCartStorage.totalSize())
        assertThat(result).isTrue()
    }

    @Test
    fun `현재 페이지가 초기 페이지보다 크면 이전 페이지가 존재한다`() {
        page.moveToNextPage()
        assertThat(page.hasPreviousPage()).isTrue()
    }

    @Test
    fun `현재 페이지가 마지막 페이지이면 true를 반환한다`() {
        page.moveToNextPage()
        assertThat(page.isLastPage(fakeCartStorage.totalSize())).isTrue()
    }

    @Test
    fun `targetRange는 현재 페이지의 인덱스 범위를 반환한다`() {
        val range = page.targetRange(fakeCartStorage.totalSize())
        assertThat(range).isEqualTo(0..4)
    }
}
