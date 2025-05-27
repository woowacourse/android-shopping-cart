package woowacourse.shopping.domain

import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PageTest {

    private lateinit var page: Page

    @BeforeEach
    fun setUp() {
        page = Page(initialPage = 1, pageSize = 10)
    }

    @Test
    fun `초기 페이지 번호는 초기값과 같아야 한다`() {
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `다음 페이지로 이동하면 페이지 번호가 증가해야 한다`() {
        page.moveToNextPage()
        assertEquals(2, page.getPageNumber())
    }

    @Test
    fun `이전 페이지로 이동하면 페이지 번호가 감소해야 한다`() {
        page.moveToNextPage()  // 2로 증가
        page.moveToPreviousPage()
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `이전 페이지 이동은 초기 페이지보다 작아질 수 없다`() {
        page.moveToPreviousPage()
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `현재 페이지 오프셋 계산은 올바르게 동작해야 한다`() {
        page.moveToNextPage()  // 2페이지
        val offset = page.targetRange()
        assertEquals(10, offset.offset) // (2-1)*10
        assertEquals(10, offset.limit)
    }

    @Test
    fun `페이지 리셋은 아이템이 없을 때만 감소해야 한다`() {
        page.moveToNextPage()  // 2페이지
        page.resetToLastPageIfEmpty(0) { }  // 아이템 0개
        assertEquals(1, page.getPageNumber())
    }

    @Test
    fun `페이지 리셋은 아이템이 있으면 유지되어야 한다`() {
        page.moveToNextPage()  // 2페이지
        page.resetToLastPageIfEmpty(1) { }  // 아이템 1개
        assertEquals(2, page.getPageNumber())
    }

    @Test
    fun `이전 페이지 존재 여부를 반환한다`() {
        assertFalse(page.hasPreviousPage())
        page.moveToNextPage()
        assertTrue(page.hasPreviousPage())
    }
}

