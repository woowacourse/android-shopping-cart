package woowacourse.shopping.presentation.custom

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.view.custom.CountContract
import woowacourse.shopping.presentation.view.custom.CountPresenter

class CountPresenterTest {
    private lateinit var presenter: CountContract.Presenter
    private lateinit var view: CountContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = CountPresenter(view)
    }

    @Test
    fun `현재 개수가 2일 때, 마이너스를 누르면 현재 개수는 1이 된다`() {
        // given
        val count = 2
        presenter.updateCount(count)

        val slot = slot<Int> ()
        justRun { view.setCountTextView(capture(slot)) }

        // when
        presenter.updateMinusCount()

        // then
        val actual = slot.captured
        val expected = 1
        assertEquals(expected, actual)
        verify { view.setCountTextView(actual) }
    }

    @Test
    fun `현재 개수가 0일 때, 마이너스를 누르면 현재 개수는 0이 된다`() {
        // given
        val count = 0
        presenter.updateCount(count)

        val slot = slot<Int> ()
        justRun { view.setCountTextView(capture(slot)) }

        // when
        presenter.updateMinusCount()

        // then
        val actual = slot.captured
        val expected = 0
        assertEquals(expected, actual)
        verify { view.setCountTextView(actual) }
    }

    @Test
    fun `현재 개수가 1일 때, 플러스를 누르면 현재 개수는 2이 된다`() {
        // given
        val count = 1
        presenter.updateCount(count)

        val slot = slot<Int> ()
        justRun { view.setCountTextView(capture(slot)) }

        // when
        presenter.updatePlusCount()

        // then
        val actual = slot.captured
        val expected = 2
        assertEquals(expected, actual)
        verify { view.setCountTextView(actual) }
    }

    @Test
    fun `현재 개수가 99일 때, 플러스를 누르면 현재 개수는 99이 된다`() {
        // given
        val count = 99
        presenter.updateCount(count)

        val slot = slot<Int> ()
        justRun { view.setCountTextView(capture(slot)) }

        // when
        presenter.updatePlusCount()

        // then
        val actual = slot.captured
        val expected = 99
        assertEquals(expected, actual)
        verify { view.setCountTextView(actual) }
    }
}
