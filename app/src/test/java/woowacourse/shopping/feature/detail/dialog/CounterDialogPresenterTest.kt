package woowacourse.shopping.feature.detail.dialog

import com.example.domain.datasource.productsDatasource
import com.example.domain.repository.CartRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.mapper.toPresentation

internal class CounterDialogPresenterTest {
    private lateinit var view: CounterDialogContract.View
    private lateinit var presenter: CounterDialogContract.Presenter
    private lateinit var cartRepository: CartRepository

    @Before
    fun init() {
        view = mockk()
        cartRepository = mockk(relaxed = true)
        presenter = CounterDialogPresenter(
            view,
            cartRepository,
            mockProduct.toPresentation().apply { count = 3 }
        )
    }

    @Test
    fun `상품 갯수 값으로 뷰의 카운트가 초기화 된다`() {
        // given
        val slot = slot<Int>()
        every { view.setCountState(capture(slot)) } just Runs

        // when
        presenter.initPresenter()

        // then
        assert(3 == slot.captured)
    }

    @Test
    fun `카운터뷰에서 변경한 숫자를 프레젠터에서 기억한다`() {
        // when
        presenter.changeCount(5)

        // then
        assert(presenter.changeCount == 5)
    }

    @Test
    fun `담기 버튼을 누르면, 카운터뷰에서 변경한 숫자대로 실제로 저장하고 다이얼로그를 종료한다`() {
        // given
        presenter.changeCount(5)
        val slot = slot<Int>()
        every { cartRepository.changeCartProductCount(any(), capture(slot)) } just Runs
        every { view.notifyChangeApplyCount(any()) } just Runs
        every { view.exit() } just Runs

        // when
        presenter.addCart()

        verify { view.notifyChangeApplyCount(5) }
        verify { view.exit() }
        assert(5 == slot.captured)
    }

    private val mockProduct = productsDatasource[1]
}
