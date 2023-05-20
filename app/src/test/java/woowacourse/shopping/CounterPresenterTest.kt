package woowacourse.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.presentation.common.CounterContract
import woowacourse.shopping.presentation.common.CounterPresenter

class CounterPresenterTest {
    private lateinit var view: CounterContract.View
    private lateinit var presenter: CounterContract.Presenter

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = CounterPresenter(view, 1)
    }

    @Test
    fun `+버튼을_누르면_1을_더한_값을_표시한다`() {
        // given
        presenter = CounterPresenter(view, 1)
        // when
        presenter.plusCount()
        // then
        assertEquals(2, presenter.counter.value.value)
    }

    @Test
    fun `-버튼을_누르면_1을_감소한_값을_표시한다`() {
        // given
        presenter = CounterPresenter(view, 1)
        // when
        presenter.minusCount()
        // then
        assertEquals(0, presenter.counter.value.value)
    }

    @Test
    fun `Counter 에 특정 값을 표시할 수 있다`() {
        // given
        presenter = CounterPresenter(view, 1)
        // when
        presenter.updateCount(3)
        // then
        assertEquals(3, presenter.counter.value.value)
    }

    @Test
    fun `Counter의 값이 0이라면 Counter가 안보인다`() {
        // given
        presenter = CounterPresenter(view, 0)
        // when
        presenter.checkCounterVisibility()
        // then
        every { view.setCounterVisibility(false) }
    }

    @Test
    fun `Counter의 값이 0이 아니라면 Counter가 보인다`() {
        // given
        presenter = CounterPresenter(view, 1)
        // when
        presenter.checkCounterVisibility()
        // then
        every { view.setCounterVisibility(true) }
    }
}
