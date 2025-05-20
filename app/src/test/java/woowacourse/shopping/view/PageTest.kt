package woowacourse.shopping.view

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.FakeCartStorage
import woowacourse.shopping.domain.Page
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

class PageTest {
    private lateinit var page: Page
    private lateinit var fakeCartStorage: FakeCartStorage

    @Before
    fun setUp() {
        page = Page()
        fakeCartStorage =
            FakeCartStorage(
                products =
                    mutableListOf(
                        Product(1L, "맥북", Price(1000), ""),
                        Product(2L, "아이폰", Price(2000), ""),
                        Product(3L, "에어팟", Price(3000), ""),
                        Product(4L, "매직키보드", Price(4000), ""),
                        Product(5L, "에어팟맥스", Price(5000), ""),
                    ),
            )
    }

    @Test
    fun `다음 페이지 이동 후 페이지 번호는 증가해야 한다`() {
        page.moveToNextPage()
        assertEquals(2, page.getPageNumber())
    }

    @Test
    fun `이전 페이지 이동은 초기 페이지보다 작아질 수 없다`() {
        page.moveToPreviousPage()
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `이전 페이지 이동은 2페이지 이상일 때만 감소해야 한다`() {
        page.moveToNextPage()
        page.moveToPreviousPage()
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `페이지가 비어있으면 이전 페이지로 이동하고 true를 반환해야 한다`() {
        page.moveToNextPage()
        val result = page.resetToLastPageIfEmpty(emptyList())
        assertTrue(result)
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `페이지가 비어있지 않으면 이동하지 않고 false를 반환해야 한다`() {
        val result =
            page.resetToLastPageIfEmpty(
                listOf(Product(999L, "dummy", Price(1234), "")),
            )
        assertFalse(result)
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `다음 페이지 존재 시 nextPageEnabled는 true를 가진다`() {
        val state = page.createPageState(fakeCartStorage)
        assertTrue(state.nextPageEnabled)
        assertTrue(state.pageVisibility)
    }

    @Test
    fun `현재 페이지가 초기보다 크면 previousPageEnabled는 true를 가진다`() {
        page.moveToNextPage()
        val state = page.createPageState(fakeCartStorage)
        assertTrue(state.previousPageEnabled)
    }
}
