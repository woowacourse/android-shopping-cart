package woowacourse.shopping.view

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.cart.vm.Paging

class PagingTest {
    private lateinit var paging: Paging

    @Before
    fun setUp() {
        paging = Paging()
    }

    @Test
    fun `다음_페이지_이동_후_페이지_번호는_증가해야_한다`() {
        paging.moveToNextPage()
        assertEquals(2, paging.getPageNo())
    }

    @Test
    fun `이전_페이지_이동은_초기_페이지보다_작아질_수_없다`() {
        paging.moveToPreviousPage()
        assertEquals(1, paging.getPageNo())
    }

    @Test
    fun `이전_페이지_이동은_2페이지_이상일_때만_감소해야_한다`() {
        paging.moveToNextPage()
        paging.moveToPreviousPage()
        assertEquals(1, paging.getPageNo())
    }

    @Test
    fun `페이지가_비어있으면_이전_페이지로_이동하고_true를_반환해야_한다`() {
        paging.moveToNextPage()
        val result = paging.resetToLastPageIfEmpty(emptyList())
        assertTrue(result)
        assertEquals(1, paging.getPageNo())
    }

    @Test
    fun `페이지가_비어있지_않으면_이동하지_않고_false를_반환해야_한다`() {
        val result =
            paging.resetToLastPageIfEmpty(
                listOf(Product(999L, "dummy", Price(1234), "")),
            )
        assertFalse(result)
        assertEquals(1, paging.getPageNo())
    }

    @Test
    fun `다음_페이지_존재_시_nextPageEnabled는_true를_가진다`() {
        val state = paging.createPageState(true)
        assertTrue(state.nextPageEnabled)
        assertTrue(state.pageVisibility)
    }

    @Test
    fun `현재_페이지가_초기보다_크면_previousPageEnabled는_true를_가진다`() {
        paging.moveToNextPage()
        val state = paging.createPageState(false)
        assertTrue(state.previousPageEnabled)
    }
}
